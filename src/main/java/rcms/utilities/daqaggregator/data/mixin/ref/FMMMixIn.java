package rcms.utilities.daqaggregator.data.mixin.ref;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import rcms.utilities.daqaggregator.data.FED;
import rcms.utilities.daqaggregator.data.FMMApplication;
import rcms.utilities.daqaggregator.data.TTCPartition;

/**
 * Class configuring json serialization
 * 
 * @author Maciej Gladki (maciej.szymon.gladki@cern.ch)
 *
 */
@JsonIdentityInfo(generator = IdGenerators.FMMIdGenerator.class, property = "@id")
public interface FMMMixIn {

	@JsonProperty("ref_ttcPartition")
	@JsonIdentityReference(alwaysAsId = true)
	abstract TTCPartition getTtcPartition();

	@JsonProperty("ref_fmmApplication")
	@JsonIdentityReference(alwaysAsId = true)
	abstract FMMApplication getFmmApplication();

	@JsonProperty("ref_feds")
	@JsonIdentityReference(alwaysAsId = true)
	abstract List<FED> getFeds();

}
