package rcms.utilities.daqaggregator.data;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rcms.utilities.daqaggregator.datasource.FlashlistType;
import rcms.utilities.daqaggregator.mappers.Derivable;
import rcms.utilities.daqaggregator.mappers.FlashlistUpdatable;

/**
 * Readout Unit
 * 
 * @author Andre Georg Holzner (andre.georg.holzner@cern.ch)
 * @author Maciej Gladki (maciej.szymon.gladki@cern.ch)
 * @author Michail Vougioukas (michail.vougioukas@cern.ch)
 */

public class RU implements FlashlistUpdatable, Derivable {

	// ----------------------------------------
	// fields set at beginning of session
	// ----------------------------------------

	/** the FEDbuilder this RU corresponds to */
	private FEDBuilder fedBuilder;

	private String hostname;
	
	private int port;

	private boolean isEVM;

	private boolean masked;

	private int instance;

	// ----------------------------------------
	// fields updated periodically
	// ----------------------------------------
	
	private boolean crashed;

	private String stateName;

	private String errorMsg;

	private String warnMsg;

	private String infoMsg;

	/** events rate in kHz ? */
	private float rate;

	/** MByte per second ? */
	private float throughput;

	/**
	 * TODO: mean superfragment size in kByte?, TODO: mean over what period ?
	 */
	private float superFragmentSizeMean;

	/** spread of superfragment size in kByte ? */
	private float superFragmentSizeStddev;

	/** total rate of fragment messages going to the BUs */
	private int fragmentsInRU;

	private int eventsInRU;

	private long eventCount;

	/** requests from BUs */
	private int requests;

	private int incompleteSuperFragmentCount;
	
	/** per-BU lists, all subject to the same order of BUs */
	private List<Long> throughputPerBU;
	
	private List<Integer> buTids;
	
	private List<Integer> fragmentRatePerBU;
	
	private List<Double> retryRatePerBU;

	/** only for the EVM case */
	private int allocateRate;
	
	private double allocateRetryRate;
	
	public boolean isCrashed() {
		return crashed;
	}

	public void setCrashed(boolean crashed) {
		this.crashed = crashed;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getWarnMsg() {
		return warnMsg;
	}

	public void setWarnMsg(String warnMsg) {
		this.warnMsg = warnMsg;
	}

	public String getInfoMsg() {
		return infoMsg;
	}

	public void setInfoMsg(String infoMsg) {
		this.infoMsg = infoMsg;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getThroughput() {
		return throughput;
	}

	public void setThroughput(float throughput) {
		this.throughput = throughput;
	}

	public float getSuperFragmentSizeMean() {
		return superFragmentSizeMean;
	}

	public void setSuperFragmentSizeMean(float superFragmentSizeMean) {
		this.superFragmentSizeMean = superFragmentSizeMean;
	}

	public float getSuperFragmentSizeStddev() {
		return superFragmentSizeStddev;
	}

	public void setSuperFragmentSizeStddev(float superFragmentSizeStddev) {
		this.superFragmentSizeStddev = superFragmentSizeStddev;
	}

	public int getFragmentsInRU() {
		return fragmentsInRU;
	}

	public void setFragmentsInRU(int fragmentsInRU) {
		this.fragmentsInRU = fragmentsInRU;
	}

	public int getEventsInRU() {
		return eventsInRU;
	}

	public void setEventsInRU(int eventsInRU) {
		this.eventsInRU = eventsInRU;
	}

	public long getEventCount() {
		return eventCount;
	}

	public void setEventCount(long eventCount) {
		this.eventCount = eventCount;
	}

	public int getRequests() {
		return requests;
	}

	public void setRequests(int requests) {
		this.requests = requests;
	}

	public FEDBuilder getFedBuilder() {
		return fedBuilder;
	}

	public String getHostname() {
		return hostname;
	}

	public boolean isEVM() {
		return isEVM;
	}

	public boolean isMasked() {
		return masked;
	}

	public void setFedBuilder(FEDBuilder fedBuilder) {
		this.fedBuilder = fedBuilder;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setEVM(boolean isEVM) {
		this.isEVM = isEVM;
	}

	public void setMasked(boolean masked) {
		this.masked = masked;
	}

	public int getInstance() {
		return instance;
	}

	public void setInstance(int instance) {
		this.instance = instance;
	}

	public List<Long> getThroughputPerBU() {
		return throughputPerBU;
	}

	public void setThroughputPerBU(List<Long> throughputPerBU) {
		this.throughputPerBU = throughputPerBU;
	}

	public List<Integer> getBuTids() {
		return buTids;
	}

	public void setBuTids(List<Integer> buTids) {
		this.buTids = buTids;
	}

	public List<Integer> getFragmentRatePerBU() {
		return fragmentRatePerBU;
	}

	public void setFragmentRatePerBU(List<Integer> fragmentRatePerBU) {
		this.fragmentRatePerBU = fragmentRatePerBU;
	}

	public List<Double> getRetryRatePerBU() {
		return retryRatePerBU;
	}

	public void setRetryRatePerBU(List<Double> retryRatePerBU) {
		this.retryRatePerBU = retryRatePerBU;
	}

	public int getAllocateRate() {
		return allocateRate;
	}

	public void setAllocateRate(int allocateRate) {
		this.allocateRate = allocateRate;
	}

	public double getAllocateRetryRate() {
		return allocateRetryRate;
	}

	public void setAllocateRetryRate(double allocateRetryRate) {
		this.allocateRetryRate = allocateRetryRate;
	}

	@Override
	public String toString() {
		return "RU [rate=" + rate + ", throughput=" + throughput + ", superFragmentSizeMean=" + superFragmentSizeMean
				+ ", superFragmentSizeStddev=" + superFragmentSizeStddev + ", fragmentsInRU=" + fragmentsInRU
				+ ", eventsInRU=" + eventsInRU + ", eventCount=" + eventCount + "]";
	}


	public int getIncompleteSuperFragmentCount() {
		return incompleteSuperFragmentCount;
	}

	public void setIncompleteSuperFragmentCount(int incompleteSuperFragmentCount) {
		this.incompleteSuperFragmentCount = incompleteSuperFragmentCount;
	}

	/** @return the list of all (real) FEDs (not including pseudofeds) associated
	 *  to this RU.
	 *  @param includeMasked if true, includes also masked FEDs, otherwise
	 *    just includes FEDs which are not masked FRL-wise.
	 */
	public Set<FED> getFEDs(boolean includeMasked) {

		Set<FED> retval = new HashSet<>();

		FEDBuilder fb = this.getFedBuilder();

		if (fb != null) {

			List<SubFEDBuilder> subFedBuilders = fb.getSubFedbuilders();

			if (subFedBuilders != null) {

				for (SubFEDBuilder subFedBuilder : subFedBuilders) {

					List<FRL> frls = subFedBuilder.getFrls();

					if (frls != null) {

						// assume that there are no null pointers in the list
						for (FRL frl : frls) {

							Map<Integer, FED> feds = frl.getFeds();

							if (feds != null) {

								// assume the map does not contain null pointers
								for (FED fed : frl.getFeds().values()) {

									if (includeMasked || ! fed.isFrlMasked()) {
										retval.add(fed);
									}

								} // loop over FEDs

							} // if feds is not null

						} // loop over FRLs

					} // if frls is not null

				} // loop over subfedbuilders

			} // if subFedBuilders is not null

		} // if fb not null

		return retval;
	}

	@Override
	public void calculateDerivedValues() {

		masked = false;
		int maskedFeds = 0;
		int allFeds = 0;

		//this will iterate over FEDs which are not pseudoFEDs, so no need to check if they have SLINK
		for (SubFEDBuilder subFedBuilder : fedBuilder.getSubFedbuilders()) {
			for (FRL frl : subFedBuilder.getFrls()) {
				for (FED fed : frl.getFeds().values()) {
					allFeds++;
					if (fed.isFrlMasked()) {
						maskedFeds++;
					}
				}
			}
		}

		/* Ru is masked if all FEDs are masked */
		if (maskedFeds == allFeds) {
			masked = true;
		}

	}

	/**
	 * Update object based on given flashlist fragment
	 * 
	 * @param flashlistRow
	 *            JsonNode representing one row from flashlist
	 */
	@Override
	public void updateFromFlashlist(FlashlistType flashlistType, JsonNode flashlistRow) {
		
		/* ignore data from RU flashlist for EVM */
		if (isEVM && flashlistType == FlashlistType.RU)
			return;

		/**
		 * For dispatching Flashlist RU to RU objects and flashist EVM to EVM
		 * objects see {@link FlashlistDispatcher}
		 */
		if (flashlistType == FlashlistType.RU || flashlistType == FlashlistType.EVM) {
			// direct values

			this.setStateName(flashlistRow.get("stateName").asText());
			this.setErrorMsg(flashlistRow.get("errorMsg").asText());
			this.requests = flashlistRow.get("activeRequests").asInt();
			this.port = Integer.parseInt(flashlistRow.get("context").asText().split(":")[2]);
			this.rate = flashlistRow.get("eventRate").asInt();
			this.eventsInRU = flashlistRow.get("eventsInRU").asInt();
			this.eventCount = flashlistRow.get("eventCount").asLong();
			this.superFragmentSizeMean = flashlistRow.get("superFragmentSize").asInt();
			this.superFragmentSizeStddev = flashlistRow.get("superFragmentSizeStdDev").asInt();
			this.incompleteSuperFragmentCount = flashlistRow.get("incompleteSuperFragmentCount").asInt();
			this.fragmentsInRU = flashlistRow.get("incompleteSuperFragmentCount").asInt();

			// derived values
			this.throughput = rate * superFragmentSizeMean;
			
			// lists of values per BU
			
			this.throughputPerBU = new ArrayList<Long>();
			this.buTids = new ArrayList<Integer>();
			this.fragmentRatePerBU = new ArrayList<Integer>();
			this.retryRatePerBU = new ArrayList<Double>();
			
			JsonNode flashlist_tempArray;
			int size;
			
			flashlist_tempArray = flashlistRow.get("throughputPerBU");
			size = flashlist_tempArray.size();
			for (int j=0;j<size;j++){
				this.throughputPerBU.add(flashlist_tempArray.get(j).asLong());
			}
			
			flashlist_tempArray = flashlistRow.get("buTids");
			size = flashlist_tempArray.size();
			for (int j=0;j<size;j++){
				this.buTids.add(flashlist_tempArray.get(j).asInt());
			}
			
			flashlist_tempArray = flashlistRow.get("fragmentRatePerBU");
			size = flashlist_tempArray.size();
			for (int j=0;j<size;j++){
				this.fragmentRatePerBU.add(flashlist_tempArray.get(j).asInt());
			}
			
			flashlist_tempArray = flashlistRow.get("retryRatePerBU");
			size = flashlist_tempArray.size();
			for (int j=0;j<size;j++){
				this.retryRatePerBU.add(flashlist_tempArray.get(j).asDouble());
			}
			
			// values set only when RU is of type EVM
			if (flashlistType == FlashlistType.EVM){
				this.allocateRate = flashlistRow.get("allocateRate").asInt();
				this.allocateRetryRate = flashlistRow.get("allocateRate").asDouble();
			}
		}
		
		if (flashlistType == FlashlistType.JOB_CONTROL) {
			JsonNode jobTable = flashlistRow.get("jobTable");

			JsonNode rows = jobTable.get("rows");

			for (JsonNode row : rows) {
				//TODO: get the row with matching jid to the context (additional field)
				String status = row.get("status").asText();

				// if not alive than crashed, if no data than default value
				// witch is not crashed
				if (!status.equalsIgnoreCase("alive"))
					this.crashed = true;

			}

		}
	}


	@Override
	public void clean() {
		this.setStateName(null);
		this.setErrorMsg(null);
		this.requests = 0;
		this.port = 0;
		this.rate = 0;
		this.eventsInRU = 0;
		this.eventCount = 0;
		this.superFragmentSizeMean = 0;
		this.superFragmentSizeStddev = 0;
		this.incompleteSuperFragmentCount = 0;
		this.fragmentsInRU = 0;
		
		this.throughput = rate * superFragmentSizeMean;
		
		// values per BU
		this.throughputPerBU = new ArrayList<Long>();
		this.buTids = new ArrayList<Integer>();
		this.fragmentRatePerBU = new ArrayList<Integer>();
		this.retryRatePerBU = new ArrayList<Double>();

		this.allocateRate = 0;
		this.allocateRetryRate = 0;
			
		this.crashed = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + allocateRate;
		long temp;
		temp = Double.doubleToLongBits(allocateRetryRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((buTids == null) ? 0 : buTids.hashCode());
		result = prime * result + ((errorMsg == null) ? 0 : errorMsg.hashCode());
		result = prime * result + (int) (eventCount ^ (eventCount >>> 32));
		result = prime * result + eventsInRU;
		result = prime * result + ((fragmentRatePerBU == null) ? 0 : fragmentRatePerBU.hashCode());
		result = prime * result + fragmentsInRU;
		result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
		result = prime * result + port;
		result = prime * result + incompleteSuperFragmentCount;
		result = prime * result + ((infoMsg == null) ? 0 : infoMsg.hashCode());
		result = prime * result + instance;
		result = prime * result + (isEVM ? 1231 : 1237);
		result = prime * result + (masked ? 1231 : 1237);
		result = prime * result + Float.floatToIntBits(rate);
		result = prime * result + requests;
		result = prime * result + ((retryRatePerBU == null) ? 0 : retryRatePerBU.hashCode());
		result = prime * result + ((stateName == null) ? 0 : stateName.hashCode());
		result = prime * result + Float.floatToIntBits(superFragmentSizeMean);
		result = prime * result + Float.floatToIntBits(superFragmentSizeStddev);
		result = prime * result + Float.floatToIntBits(throughput);
		result = prime * result + ((throughputPerBU == null) ? 0 : throughputPerBU.hashCode());
		result = prime * result + ((warnMsg == null) ? 0 : warnMsg.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RU other = (RU) obj;
		if (allocateRate != other.allocateRate)
			return false;
		if (Double.doubleToLongBits(allocateRetryRate) != Double.doubleToLongBits(other.allocateRetryRate))
			return false;
		if (buTids == null) {
			if (other.buTids != null)
				return false;
		} else if (!buTids.equals(other.buTids))
			return false;
		if (errorMsg == null) {
			if (other.errorMsg != null)
				return false;
		} else if (!errorMsg.equals(other.errorMsg))
			return false;
		if (eventCount != other.eventCount)
			return false;
		if (eventsInRU != other.eventsInRU)
			return false;
		if (fragmentRatePerBU == null) {
			if (other.fragmentRatePerBU != null)
				return false;
		} else if (!fragmentRatePerBU.equals(other.fragmentRatePerBU))
			return false;
		if (fragmentsInRU != other.fragmentsInRU)
			return false;
		if (hostname == null) {
			if (other.hostname != null)
				return false;
		} else if (!hostname.equals(other.hostname))
			return false;
		if (incompleteSuperFragmentCount != other.incompleteSuperFragmentCount)
			return false;
		if (port != other.port)
			return false;
		if (infoMsg == null) {
			if (other.infoMsg != null)
				return false;
		} else if (!infoMsg.equals(other.infoMsg))
			return false;
		if (instance != other.instance)
			return false;
		if (isEVM != other.isEVM)
			return false;
		if (masked != other.masked)
			return false;
		if (Float.floatToIntBits(rate) != Float.floatToIntBits(other.rate))
			return false;
		if (requests != other.requests)
			return false;
		if (retryRatePerBU == null) {
			if (other.retryRatePerBU != null)
				return false;
		} else if (!retryRatePerBU.equals(other.retryRatePerBU))
			return false;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName))
			return false;
		if (Float.floatToIntBits(superFragmentSizeMean) != Float.floatToIntBits(other.superFragmentSizeMean))
			return false;
		if (Float.floatToIntBits(superFragmentSizeStddev) != Float.floatToIntBits(other.superFragmentSizeStddev))
			return false;
		if (Float.floatToIntBits(throughput) != Float.floatToIntBits(other.throughput))
			return false;
		if (throughputPerBU == null) {
			if (other.throughputPerBU != null)
				return false;
		} else if (!throughputPerBU.equals(other.throughputPerBU))
			return false;
		if (warnMsg == null) {
			if (other.warnMsg != null)
				return false;
		} else if (!warnMsg.equals(other.warnMsg))
			return false;
		return true;
	}

	
}
