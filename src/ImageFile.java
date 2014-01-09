import java.io.File;

/**
 * Created by Simon on 2014-01-01.
 */
public class ImageFile {
	private Enums.FORMAT sourceFormat;
	private Enums.FORMAT destinationFormat;
	private Enums.OUTPUT output;

	private String fileName;
	private String filePath;
	private File file;

	public ImageFile(){
		output = Enums.OUTPUT.NONE;
	}

	public ImageFile(Enums.FORMAT sourceFormat, Enums.FORMAT destinationFormat) {
		this.sourceFormat = sourceFormat;
		this.destinationFormat = destinationFormat;
		this.output = output;
	}

	public Enums.FORMAT getSourceFormat() {
		return sourceFormat;
	}

	public Enums.FORMAT getDestinationFormat() {
		return destinationFormat;
	}

	public Enums.OUTPUT getOutput() {
		return output;
	}

	public void setSourceFormat(Enums.FORMAT sourceFormat) {
		this.sourceFormat = sourceFormat;
	}

	public void setDestinationFormat(Enums.FORMAT destinationFormat) {
		this.destinationFormat = destinationFormat;
	}

	public void setOutput(Enums.OUTPUT output) {
		this.output = output;
	}
}
