package transport.push;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import transport.controller.GeneralController;
import transport.model.Photo;
import transport.utils.PropertiesUtils;

import com.google.code.jgntp.Gntp;
import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpClient;
import com.google.code.jgntp.GntpNotificationInfo;

public abstract class Growl {
	
	public static void sendNotification(String header, String detail, String staffCode, String ip) {	
		if(isClientAvailable(ip)){
			GntpApplicationInfo info = Gntp.appInfo("Transport").build();
			GntpNotificationInfo noinfo = Gntp.notificationInfo(info, "NewRequest").build();		
			GntpClient client = Gntp.client(info).forHost(ip).secure(PropertiesUtils.getGrowlProperties().getString("password")).withoutRetry().build();
			client.register();
			
			GeneralController controller = new GeneralController();
			Photo photo = controller.getPhoto(staffCode);
			
			try {
				client.notify(Gntp.notification(noinfo, header).text(detail).icon(getImage(photo.getPhoto())).build());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
	}
	
	protected static RenderedImage getImage(byte[] image) throws IOException {
        InputStream is = new ByteArrayInputStream(image);
        try {
                return ImageIO.read(is);
        } finally {
                IOUtils.closeQuietly(is);
        }
	}
	
	private static boolean isClientAvailable(String ip){
		try {
			Socket sock = new Socket();
			sock.connect(new InetSocketAddress(ip, 23053), 500);
			System.out.println("Growl available = "+sock.isConnected());
			sock.close();
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
