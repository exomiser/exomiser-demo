package org.monarchinitiative;

import de.charite.compbio.jannovar.annotation.VariantEffect;
import org.monarchinitiative.exomiser.core.Exomiser;
import org.monarchinitiative.exomiser.core.analysis.Analysis;
import org.monarchinitiative.exomiser.core.analysis.AnalysisResults;
import org.monarchinitiative.exomiser.core.model.frequency.FrequencySource;
import org.monarchinitiative.exomiser.core.model.pathogenicity.PathogenicitySource;
import org.monarchinitiative.exomiser.core.prioritisers.HiPhiveOptions;
import org.monarchinitiative.exomiser.core.prioritisers.PriorityType;
import org.monarchinitiative.exomiser.core.writers.OutputFormat;
import org.monarchinitiative.exomiser.core.writers.OutputSettings;
import org.monarchinitiative.exomiser.core.writers.ResultsWriter;
import org.monarchinitiative.exomiser.core.writers.ResultsWriterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.List;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
@RestController
public class ExomiserController {

    private final Exomiser exomiser;
    private final ResultsWriterFactory resultsWriterFactory;

    @Autowired
    public ExomiserController(Exomiser exomiser, ResultsWriterFactory resultsWriterFactory) {
        this.exomiser = exomiser;
        this.resultsWriterFactory = resultsWriterFactory;
    }

    @GetMapping("analyse")
    public String analyse(@RequestParam("vcf") String vcf, @RequestParam("phenotypes") List<String> phenotypes, @RequestParam("format") OutputFormat outputFormat) {
        Analysis analysis = exomiser.getAnalysisBuilder()
                .vcfPath(Paths.get(vcf))
                .hpoIds(phenotypes)
                .frequencySources(FrequencySource.ALL_EXTERNAL_FREQ_SOURCES)
                .pathogenicitySources(EnumSet.of(PathogenicitySource.SIFT, PathogenicitySource.POLYPHEN, PathogenicitySource.MUTATION_TASTER))
                .addHiPhivePrioritiser(HiPhiveOptions.builder().runParams("human,mouse,fish").build())
                .addPriorityScoreFilter(PriorityType.HIPHIVE_PRIORITY, 0.501f)
                .addVariantEffectFilter(EnumSet.of(VariantEffect.UPSTREAM_GENE_VARIANT,
                        VariantEffect.INTERGENIC_VARIANT,
                        VariantEffect.DOWNSTREAM_GENE_VARIANT,
                        VariantEffect.CODING_TRANSCRIPT_INTRON_VARIANT,
                        VariantEffect.NON_CODING_TRANSCRIPT_INTRON_VARIANT,
                        VariantEffect.SYNONYMOUS_VARIANT,
                        VariantEffect.SPLICE_REGION_VARIANT,
                        VariantEffect.REGULATORY_REGION_VARIANT))
                .addFrequencyFilter(0.1f)
                .addPathogenicityFilter(true)
                .addOmimPrioritiser()
                .build();
        AnalysisResults analysisResults = exomiser.run(analysis);

        OutputSettings outputSettings = OutputSettings.builder()
                .outputPassVariantsOnly(true)
                .numberOfGenesToShow(30)
                .build();

        ResultsWriter writer = resultsWriterFactory.getResultsWriter(outputFormat);
        return writer.writeString(analysis, analysisResults, outputSettings);
    }
}
