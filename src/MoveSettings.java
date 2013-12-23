/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 2013-12-23
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */


public class MoveSettings {
	Enums.FORMAT fromFolder;
	Enums.FORMAT toFormat;

	public MoveSettings(Enums.FORMAT fromFolder, Enums.FORMAT toFormat) {
		this.fromFolder = fromFolder;
		this.toFormat = toFormat;
	}

	public Enums.FORMAT getFromFolder () {
		return fromFolder;
	}
	public Enums.FORMAT getToFormat() {
		return toFormat;
	}
	public void setFromFolder(Enums.FORMAT fromFolder) {
		this.fromFolder = fromFolder;
	}

	public void setToFormat(Enums.FORMAT toFormat) {
		this.toFormat = toFormat;
	}
}