package rcms.utilities.daqaggregator.data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import rcms.utilities.daqaggregator.mappers.Derivable;

/**
 * Summary statistics of FED builders
 * 
 * @author Andre Georg Holzner (andre.georg.holzner@cern.ch)
 * @author Maciej Gladki (maciej.szymon.gladki@cern.ch)
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class FEDBuilderSummary implements Serializable , Derivable{

	// ----------------------------------------
	// fields set at beginning of session
	// ----------------------------------------

	/** parent */
	private DAQ daq;

	// ----------------------------------------
	// fields updated periodically
	// ----------------------------------------

	/** event building rate in kHz */
	private float rate;

	/** throughput in MByte/s ? */
	private float throughput;

	/** mean superfragment size in kByte ? */
	private float superFragmentSizeMean;

	/** spread of superfragment size in kByte ? */
	private float superFragmentSizeStddev;

	/**
	 * difference of number of events in RU between highest and lowest
	 * fedbuilder ???
	 */
	private int deltaEvents;

	private int sumFragmentsInRU;

	private int sumEventsInRU;

	/** requests from BUs ? */
	private long sumRequests;

	// ----------------------------------------------------------------------

	// ----------------------------------------------------------------------

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

	public int getDeltaEvents() {
		return deltaEvents;
	}

	public void setDeltaEvents(int deltaEvents) {
		this.deltaEvents = deltaEvents;
	}

	public int getSumFragmentsInRU() {
		return sumFragmentsInRU;
	}

	public void setSumFragmentsInRU(int sumFragmentsInRU) {
		this.sumFragmentsInRU = sumFragmentsInRU;
	}

	public int getSumEventsInRU() {
		return sumEventsInRU;
	}

	public void setSumEventsInRU(int sumEventsInRU) {
		this.sumEventsInRU = sumEventsInRU;
	}

	public long getSumRequests() {
		return sumRequests;
	}

	public void setSumRequests(long sumRequests) {
		this.sumRequests = sumRequests;
	}

	public DAQ getDaq() {
		return daq;
	}

	public void setDaq(DAQ daq) {
		this.daq = daq;
	}

	// ----------------------------------------------------------------------

	@Override
	public void calculateDerivedValues() {
		this.setDaq(daq);
		int numberOfRus = daq.getFedBuilders().size();

		/* delta between min and max (min not 0) */
		int deltaEvents = 0;
		int maxEvents = Integer.MIN_VALUE;
		int minEvents = Integer.MAX_VALUE;

		/* Averages */
		float superFragmentSizeMean = 0;
		float superFragmentSizeStddev = 0;
		float rate = 0;

		/* Sums */
		float throughput = 0;
		int sumEventsInRU = 0;
		int sumFragmentsInRU = 0;
		long sumRequests = 0;

		for (FEDBuilder fb : daq.getFedBuilders()) {
			RU ru = fb.getRu();
			rate += ru.getRate();
			sumEventsInRU += ru.getEventsInRU();
			sumFragmentsInRU += ru.getFragmentsInRU();
			sumRequests += ru.getRequests();
			superFragmentSizeMean += ru.getSuperFragmentSizeMean();
			superFragmentSizeStddev += ru.getSuperFragmentSizeStddev();
			throughput += ru.getThroughput();

			if (maxEvents < ru.getEventsInRU())
				maxEvents = ru.getEventsInRU();
			if (minEvents > ru.getEventsInRU() && ru.getEventsInRU() != 0) {
				minEvents = ru.getEventsInRU();
			}

		}

		/* avarage values */
		superFragmentSizeMean = superFragmentSizeMean / (float) numberOfRus;
		superFragmentSizeStddev = superFragmentSizeStddev / (float) numberOfRus;
		rate = rate / (float) numberOfRus;

		/* deltas */
		this.setDeltaEvents(maxEvents - minEvents);

		this.setDeltaEvents(deltaEvents);
		this.setRate(rate);
		this.setSumEventsInRU(sumEventsInRU);
		this.setSumFragmentsInRU(sumFragmentsInRU);
		this.setSumRequests(sumRequests);
		this.setSuperFragmentSizeMean(superFragmentSizeMean);
		this.setSuperFragmentSizeStddev(superFragmentSizeStddev);
		this.setThroughput(throughput);

	}
}
