/**
 * 
 */
package uk.ac.cam.kilo.backend;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * @author group_kilo
 *
 */
public class BarcodeServer extends HttpServer {

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpServer#bind(java.net.InetSocketAddress, int)
	 */
	@Override
	public void bind(InetSocketAddress arg0, int arg1) throws IOException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpServer#createContext(java.lang.String)
	 */
	@Override
	public HttpContext createContext(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpServer#createContext(java.lang.String, com.sun.net.httpserver.HttpHandler)
	 */
	@Override
	public HttpContext createContext(String arg0, HttpHandler arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpServer#getAddress()
	 */
	@Override
	public InetSocketAddress getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpServer#getExecutor()
	 */
	@Override
	public Executor getExecutor() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpServer#removeContext(java.lang.String)
	 */
	@Override
	public void removeContext(String arg0) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpServer#removeContext(com.sun.net.httpserver.HttpContext)
	 */
	@Override
	public void removeContext(HttpContext arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpServer#setExecutor(java.util.concurrent.Executor)
	 */
	@Override
	public void setExecutor(Executor arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpServer#start()
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpServer#stop(int)
	 */
	@Override
	public void stop(int arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
