package de.rwth.i9.cimt.controller;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.openrdf.model.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import de.tudarmstadt.ukp.wikipedia.api.Category;
import de.tudarmstadt.ukp.wikipedia.api.DatabaseConfiguration;
import de.tudarmstadt.ukp.wikipedia.api.Page;
import de.tudarmstadt.ukp.wikipedia.api.WikiConstants.Language;
import de.tudarmstadt.ukp.wikipedia.api.Wikipedia;
import de.tudarmstadt.ukp.wikipedia.api.exception.WikiApiException;
import de.tudarmstadt.ukp.wikipedia.api.exception.WikiTitleParsingException;
import slib.graph.algo.utils.GAction;
import slib.graph.algo.utils.GActionType;
import slib.graph.algo.utils.GraphActionExecutor;
import slib.graph.algo.validator.dag.ValidatorDAG;
import slib.graph.io.conf.GDataConf;
import slib.graph.io.loader.wordnet.GraphLoader_Wordnet;
import slib.graph.io.util.GFormat;
import slib.graph.model.graph.G;
import slib.graph.model.impl.graph.memory.GraphMemory;
import slib.graph.model.impl.repo.URIFactoryMemory;
import slib.graph.model.repo.URIFactory;
import slib.indexer.wordnet.IndexerWordNetBasic;
import slib.sml.sm.core.engine.SM_Engine;
import slib.sml.sm.core.metrics.ic.utils.IC_Conf_Topo;
import slib.sml.sm.core.metrics.ic.utils.ICconf;
import slib.sml.sm.core.utils.SMConstants;
import slib.sml.sm.core.utils.SMconf;

@Configuration
@RestController
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHome() {
		return "CIMT Up & Running";
	}

	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	public String view1() {
		log.info("Inside the message/view1");
		try {

			// Location of WordNet Data
			String dataloc = "C:\\rks\\Thesis\\Workspace\\ComprehensiveInterestModellingToolkit\\src\\main\\resources\\en\\wordnet3.1\\";

			// We create the graph
			URIFactory factory = URIFactoryMemory.getSingleton();
			URI guri = factory.getURI("http://graph/wordnet/");
			G wordnet = new GraphMemory(guri);

			// We load the data into the graph
			GraphLoader_Wordnet loader = new GraphLoader_Wordnet();

			GDataConf dataNoun = new GDataConf(GFormat.WORDNET_DATA, dataloc + "data.noun");
			GDataConf dataVerb = new GDataConf(GFormat.WORDNET_DATA, dataloc + "data.verb");
			GDataConf dataAdj = new GDataConf(GFormat.WORDNET_DATA, dataloc + "data.adj");
			GDataConf dataAdv = new GDataConf(GFormat.WORDNET_DATA, dataloc + "data.adv");

			loader.populate(dataNoun, wordnet);
			loader.populate(dataVerb, wordnet);
			loader.populate(dataAdj, wordnet);
			loader.populate(dataAdv, wordnet);

			// We root the graph which has been loaded (this is optional but may
			// be required to compare synset which do not share common
			// ancestors).
			GAction addRoot = new GAction(GActionType.REROOTING);
			GraphActionExecutor.applyAction(addRoot, wordnet);

			// This is optional. It just shows which are the synsets which are
			// not subsumed
			ValidatorDAG validatorDAG = new ValidatorDAG();
			Set<URI> roots = validatorDAG.getTaxonomicRoots(wordnet);
			System.out.println("Roots: " + roots);

			// We create an index to map the nouns to the vertices of the graph
			// We only build an index for the nouns in this example
			String data_noun = dataloc + "index.noun";

			IndexerWordNetBasic indexWordnetNoun = new IndexerWordNetBasic(factory, wordnet, data_noun);

			// uncomment if you want to show the index, i.e. nouns and
			// associated URIs (identifiers)
			for (Map.Entry<String, Set<URI>> entry : indexWordnetNoun.getIndex().entrySet()) {
				System.out.println(entry.getKey() + "\t" + entry.getValue());
			}

			// We focus on three specific nouns in this example
			// - iced_coffee [http://graph/wordnet/07936780]
			// - instant_coffee [http://graph/wordnet/07936903]
			// - green_tea [http://graph/wordnet/07951392]
			// We retrive their identifiers
			Set<URI> uris_iced_coffee = indexWordnetNoun.get("iced_coffee");
			Set<URI> uris_instant_coffee = indexWordnetNoun.get("instant_coffee");
			Set<URI> uris_green_tea = indexWordnetNoun.get("green_tea");

			// Note that multiple URIs (identifiers) can be associated to the
			// same noun
			// In this example we only consider nouns associated to a single URI
			// so we retrieve their URI
			URI uri_iced_coffee = uris_iced_coffee.iterator().next();
			URI uri_instant_coffee = uris_instant_coffee.iterator().next();
			URI uri_green_tea = uris_green_tea.iterator().next();

			// We configure a pairwise semantic similarity measure,
			// i.e., a measure which will be used to assess the similarity
			// of two nouns regarding their associated vertices in WordNet
			ICconf iconf = new IC_Conf_Topo(SMConstants.FLAG_ICI_SECO_2004);
			SMconf measureConf = new SMconf(SMConstants.FLAG_SIM_PAIRWISE_DAG_NODE_LIN_1998);
			measureConf.setICconf(iconf);

			// We define the engine used to compute the score of the configured
			// measure
			// several preprocessing will be made prior to the first
			// computation, e.g. to compute the Information Content (IC)
			// of the vertices. This may take some few secondes
			SM_Engine engine = new SM_Engine(wordnet);

			// we compute the semantic similarities
			double sim_iced_coffee_vs_instant_coffee = engine.compare(measureConf, uri_iced_coffee, uri_instant_coffee);
			double sim_iced_coffee_vs_green_tea = engine.compare(measureConf, uri_iced_coffee, uri_green_tea);

			// That's it
			System.out.println("sim(iced_coffee,instant_coffee) = " + sim_iced_coffee_vs_instant_coffee);
			System.out.println("sim(iced_coffee,green_tea)      = " + sim_iced_coffee_vs_green_tea);

			// Which prints
			// sim(iced_coffee,instant_coffee) = 0.7573022852697784
			// sim(iced_coffee,green_tea) = 0.3833914674618656

		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return "";
	}

	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	public ModelAndView view2() {
		log.info("Inside the message/view1");
		return new ModelAndView("messages/view", "message", "");
	}

	@RequestMapping(value = "/test3", method = RequestMethod.GET)
	public ModelAndView view3() {
		log.info("Inside the message/view3");

		// configure the database connection parameters
		DatabaseConfiguration dbConfig = new DatabaseConfiguration();
		dbConfig.setHost("localhost");
		dbConfig.setDatabase("simplewikidb");
		dbConfig.setUser("wikiuser");
		dbConfig.setPassword("wikiuser");
		dbConfig.setLanguage(Language.simple_english);

		// Create a new German wikipedia.
		Wikipedia wiki;
		try {
			wiki = new Wikipedia(dbConfig);
			// Get the page with title "Hello world".
			// May throw an exception, if the page does not exist.
			Page page = wiki.getPage("April");
			System.out.println(page.getText());
		} catch (WikiApiException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}

		return new ModelAndView("messages/view", "message", "");
	}

	@RequestMapping(value = "/test4", method = RequestMethod.GET)
	public ModelAndView view4() {
		log.info("Inside the message/view3");

		// configure the database connection parameters
		DatabaseConfiguration dbConfig = new DatabaseConfiguration();
		dbConfig.setHost("localhost");
		dbConfig.setDatabase("simplewikidb");
		dbConfig.setUser("wikiuser");
		dbConfig.setPassword("wikiuser");
		dbConfig.setLanguage(Language.simple_english);

		// Create a new German wikipedia.
		Wikipedia wiki;
		try {
			wiki = new Wikipedia(dbConfig);
			// Get the page with title "Hello world".
			// May throw an exception, if the page does not exist.
			Page page = wiki.getPage("April");
			Set<Category> cate = page.getCategories();
			cate.forEach(e -> {
				try {
					System.out.println(e.getTitle());
				} catch (WikiTitleParsingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			System.out.println(page.getText());
		} catch (WikiApiException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}

		return new ModelAndView("messages/view", "message", "");
	}

}
