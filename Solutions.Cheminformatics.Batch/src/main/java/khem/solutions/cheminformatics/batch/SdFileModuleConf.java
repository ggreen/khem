package khem.solutions.cheminformatics.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.dsl.IntegrationFlow;
//import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;



//@Configuration
//@EnableIntegration
//@Import({PrefixAndSuffixConfiguration.class, PrefixOnlyConfiguration.class})
public class SdFileModuleConf
{
	@Autowired
	GenericTransformer<String,String> transformer;

	@Bean
	public MessageChannel input() {
		return new DirectChannel();
	}

	@Bean
	MessageChannel output() {
		return new DirectChannel();
	}

	/*@Bean
	public IntegrationFlow myFlow() {
		return IntegrationFlows.from(this.input())
				.transform(transformer)
				.channel(this.output())
				.get();
	}*/

	@Configuration
	@Profile({"use-both","default"})
	class PrefixAndSuffixConfiguration {

		@Value("${prefix}")
		private String prefix;

		@Value("${suffix:}")
		private String suffix;

		@Bean
		GenericTransformer<String, String> transformer() {
			return new GenericTransformer<String, String>() {
				@Override
				public String transform(String payload) {
					return prefix + payload + suffix;
				}
			};
		}
	}
	
	@Configuration
	@Profile("use-prefix")
	class PrefixOnlyConfiguration {

		@Value("${prefix}")
		private String prefix;

		@Bean
		GenericTransformer<String, String> transformer() {
			return new GenericTransformer<String, String>() {
				@Override
				public String transform(String payload) {
					return prefix + payload;
				}
			};
		}
	}
}
