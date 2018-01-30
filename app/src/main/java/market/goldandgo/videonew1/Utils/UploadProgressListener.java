package market.goldandgo.videonew1.Utils;
/**
 * Upload Listener
 *
 */
public interface UploadProgressListener {
	/**
	 * This method updated how much data size uploaded to server
	 * @param num
	 */
	void transferred(long num);
}
