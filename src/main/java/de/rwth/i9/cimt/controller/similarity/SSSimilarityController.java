package de.rwth.i9.cimt.controller.similarity;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.rwth.i9.cimt.model.SimilarityVector;
import de.rwth.i9.cimt.service.similarity.SimilarityRelatednessService;

@RestController
@RequestMapping("/similarity/ss")
public class SSSimilarityController {

	private static final Logger log = LoggerFactory.getLogger(SSSimilarityController.class);

	@Autowired
	SimilarityRelatednessService ssSimilarityService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public double computeRelatednessScoreTokens(@RequestParam("token1") String token1,
			@RequestParam("token2") String token2) {
		return ssSimilarityService.computeVectorRelatedness(Arrays.asList(token1), Arrays.asList(token2), "default");
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public double computeRelatednessScoreList(@RequestBody SimilarityVector sv) {
		return ssSimilarityService.computeVectorRelatedness(sv.getVector1(), sv.getVector2(),
				sv.getSimilarityAlgorithm());
	}

	@RequestMapping(value = "/vector", method = RequestMethod.POST)
	public List<List<Double>> computeRelatednessScoreWord(@RequestBody SimilarityVector sv) {
		return ssSimilarityService.computeWordRelatedness(sv.getVector1(), sv.getVector2(),
				sv.getSimilarityAlgorithm());
	}

}
