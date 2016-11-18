package de.rwth.i9.cimt.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.shef.dcs.jate.JATEException;
import uk.ac.shef.dcs.jate.JATEProperties;
import uk.ac.shef.dcs.jate.app.AppParams;
import uk.ac.shef.dcs.jate.app.AppRAKE;
import uk.ac.shef.dcs.jate.model.JATEDocument;
import uk.ac.shef.dcs.jate.model.JATETerm;
import uk.ac.shef.dcs.jate.util.JATEUtil;

public class AppRake {
	private static final Logger LOG = LoggerFactory.getLogger(AppRake.class);
	private static int id = 0;

	public static void main12(String[] args) throws JATEException, IOException, SolrServerException {
		String workingDir = System.getProperty("user.dir");
		Path solrHome = Paths.get(workingDir, "testdata", "solr-testbed");
		String solrHomeDir = solrHome.toString();
		String solrCoreName = "ACLRDTEC";

		EmbeddedSolrServer server = null;
		try {
			CoreContainer solrContainer = new CoreContainer(solrHomeDir);
			solrContainer.load();

			server = new EmbeddedSolrServer(solrContainer, solrCoreName);

			JATEDocument jateDocument = new JATEDocument("sample");
			jateDocument.setContent(
					" In the last few years, there has been a growing interest in learning analytics. \n (LA) in technology-enhanced learning (TEL). LA approaches share a movement from data to analysis to action to learning. The TEL landscape is changing. Learning is increasingly happening in open and networked learning environments, characterized by increasing complexity and fast-paced change. This should be reflected in the conceptualization and development of innovative LA approaches in order to achieve more effective learning experiences. There is a need to provide understanding into how learners learn in these environments and how learners, educators, institutions, and researchers can best support this process. In this paper, we discuss open learning analytics as an emerging research field that has the potential to deal with the challenges in open and networked environments and present key conceptual and technical ideas toward an open learning analytics ecosystem.");

			JATEProperties jateProp = new JATEProperties();
			jateDocument.setId("10");

			JATEUtil.addNewDoc(server, jateDocument.getId(), jateDocument.getId(), jateDocument.getContent(), jateProp,
					true);

			LOG.info("AppRAKE ranking and filtering ... ");
			List<JATETerm> terms = new ArrayList<>();
			Map<String, String> initParam = new HashMap<>();
			initParam.put(AppParams.PREFILTER_MIN_TERM_TOTAL_FREQUENCY.getParamKey(), "1");
			initParam.put(AppParams.CUTOFF_TOP_K_PERCENT.getParamKey(), "1");

			AppRAKE appRAKE = new AppRAKE(initParam);
			terms = appRAKE.extract(server.getCoreContainer().getCore(solrCoreName), jateProp);

			LOG.info("complete ranking and filtering.");
			assert terms != null;
			assert terms.size() == 20;
			Map<String, Double> termScoreMap = new HashMap<>();
			for (JATETerm term : terms) {
				termScoreMap.put(term.getString(), term.getScore());
				System.out.println(term.getString() + "," + term.getScore());
			}

			/**
			 * The sample results is consistent with original paper.
			 *
			 * The slight difference is caused by two reasons: 1) candidates are
			 * generated by NP chunker rather than stop words filtering; 2) in
			 * JATE 2.0, we do lemmetise/stemming in candidate terms analyser
			 * chain before actual scoring. So, terms may have higher or lower
			 * score than corresponding one in original paper if plural form
			 * exists, e.g., "set", "minimal supporting set", "system",
			 * "corresponding algorithm"
			 */
			// Assert.assertEquals(Double.valueOf(8.5),termScoreMap.get("linear
			// diophantine equation"));
			// Assert.assertEquals(Double.valueOf(7.916666666666666),termScoreMap.get("minimal
			// supporting set"));
			// Assert.assertEquals(Double.valueOf(4.916666666666666),termScoreMap.get("minimal
			// set"));
			// Assert.assertEquals(Double.valueOf(4.5),termScoreMap.get("corresponding
			// algorithm"));
			// Assert.assertEquals(Double.valueOf(4.5),termScoreMap.get("linear
			// constraint"));
			// Assert.assertEquals(Double.valueOf(4.0),termScoreMap.get("strict
			// inequation"));
			// Assert.assertEquals(Double.valueOf(4.0),termScoreMap.get("nonstrict
			// inequation"));
			// Assert.assertEquals(Double.valueOf(3.666666666666667),termScoreMap.get("mixed
			// type"));
			// Assert.assertEquals(Double.valueOf(2.5),termScoreMap.get("solutions
			// and algorithm"));
			// Assert.assertEquals(Double.valueOf(2.25),termScoreMap.get("set"));
			// Assert.assertEquals(Double.valueOf(1.6666666666666667),termScoreMap.get("type"));
			// Assert.assertEquals(Double.valueOf(3.166666666666667),termScoreMap.get("considered
			// type"));
			// Assert.assertEquals(Double.valueOf(1.4),termScoreMap.get("system"));
			// Assert.assertEquals(Double.valueOf(1.4),termScoreMap.get("systems
			// and system"));
			// Assert.assertEquals(Double.valueOf(1.0),termScoreMap.get("upper"));
			// Assert.assertEquals(Double.valueOf(1.0),termScoreMap.get("component"));
			// Assert.assertEquals(Double.valueOf(1.0),termScoreMap.get("solution"));
			// Assert.assertEquals(Double.valueOf(1.0),termScoreMap.get("construction"));
			// Assert.assertEquals(Double.valueOf(1.0),termScoreMap.get("compatibility"));
		} finally {
			if (server != null) {
				server.getCoreContainer().getCore(solrCoreName).close();
				server.getCoreContainer().shutdown();
				server.close();
			}
		}

		System.exit(0);
	}

	public static List<JATETerm> rakeAlgo(String text) throws JATEException, IOException, SolrServerException {
		String workingDir = System.getProperty("user.dir");
		Path solrHome = Paths.get(workingDir, "testdata", "solr-testbed");
		String solrHomeDir = solrHome.toString();
		List<JATETerm> terms = new ArrayList<>();
		String solrCoreName = "ACLRDTEC";
		File lock = Paths.get(solrHome.toString(), solrCoreName, "data", "index", "write.lock").toFile();
		if (lock.exists()) {
			System.err.println("Previous solr did not shut down cleanly. Unlock it ...");
			lock.delete();
		}

		EmbeddedSolrServer server = null;
		try {
			CoreContainer solrContainer = new CoreContainer(solrHomeDir);
			solrContainer.load();

			server = new EmbeddedSolrServer(solrContainer, solrCoreName);

			JATEDocument jateDocument = new JATEDocument("sample" + String.valueOf(++id));
			jateDocument.setContent(text);
			jateDocument.setId(String.valueOf(id));

			JATEProperties jateProp = new JATEProperties();

			JATEUtil.addNewDoc(server, jateDocument.getId(), jateDocument.getId(), jateDocument.getContent(), jateProp,
					true);

			LOG.info("AppRAKE ranking and filtering ... ");
			Map<String, String> initParam = new HashMap<>();
			initParam.put(AppParams.PREFILTER_MIN_TERM_TOTAL_FREQUENCY.getParamKey(), "1");
			initParam.put(AppParams.CUTOFF_TOP_K_PERCENT.getParamKey(), "1");

			AppRAKE appRAKE = new AppRAKE(initParam);
			terms = appRAKE.extract(server.getCoreContainer().getCore(solrCoreName), jateProp);
			for (JATETerm term : terms) {
				System.out.println(term.getString() + "," + term.getScore());
			}

			LOG.info("complete ranking and filtering.");

			/**
			 * The sample results is consistent with original paper.
			 *
			 * The slight difference is caused by two reasons: 1) candidates are
			 * generated by NP chunker rather than stop words filtering; 2) in
			 * JATE 2.0, we do lemmetise/stemming in candidate terms analyser
			 * chain before actual scoring. So, terms may have higher or lower
			 * score than corresponding one in original paper if plural form
			 * exists, e.g., "set", "minimal supporting set", "system",
			 * "corresponding algorithm"
			 */
		} finally {
			// if (server != null) {
			// server.getCoreContainer().getCore(solrCoreName).close();
			// // server.getCoreContainer().shutdown();
			// server.close();
			// }
		}
		return terms;

	}

}
