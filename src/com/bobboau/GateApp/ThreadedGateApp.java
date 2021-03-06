package com.bobboau.GateApp;

import java.net.URL;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Bobboau
 * a thread safe wrapper around the gateapp
 *
 */
@SuppressWarnings("synthetic-access")
public class ThreadedGateApp implements GateAppType
{
	/**
	 * the app we are wrapping
	 */
	private GateAppType my_app = null;
	
	/**
	 * commands to execute
	 */
	private BlockingQueue<Runnable> command_queue = new LinkedBlockingQueue<Runnable>();
	
	/**
	 * the thread all of my stuff happens on
	 */
	Thread my_thread = new Thread(){
		@Override
		public void run(){
			while(true){
				try
				{
					ThreadedGateApp.this.command_queue.take().run();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	};

	/**
	 * constructor that adds a listener before it does anything
	 * @param listener
	 */
	public ThreadedGateApp(final GateAppListener listener){
		try
		{
			this.command_queue.put(new Runnable(){
				@Override
				public void run()
				{
					ThreadedGateApp.this.my_app = new GateApp(listener);
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		this.my_thread.start();
	}

	/**
	 * simple constructor
	 */
	public ThreadedGateApp()
	{
		try
		{
			this.command_queue.put(new Runnable(){
				@Override
				public void run()
				{
					ThreadedGateApp.this.my_app = new GateApp();
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		this.my_thread.start();
	}

	/**
	 * passes the call to the GateApp via a blocking queue
	 */
	@Override
	public void addListener(final GateAppListener gate_listener)
	{
		try
		{
			this.command_queue.put(new Runnable(){
				@Override
				public void run()
				{
					ThreadedGateApp.this.my_app.addListener(gate_listener);
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * passes the call to the GateApp via a blocking queue
	 */
	@Override
	public void setCorpus(final URL document_directory)
	{
		try
		{
			this.command_queue.put(new Runnable(){
				@Override
				public void run()
				{
					ThreadedGateApp.this.my_app.setCorpus(document_directory);
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * passes the call to the GateApp via a blocking queue
	 */
	@Override
	public void getDocumentContent(final int idx, final ResultRetriever<String> results)
	{
		try
		{
			this.command_queue.put(new Runnable(){
				@Override
				public void run()
				{
					ThreadedGateApp.this.my_app.getDocumentContent(idx, results);
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * passes the call to the GateApp via a blocking queue
	 */
	@Override
	public void getDocumentPeople(final int idx, final ResultRetriever<List<Vertex_people>> results){
		
		try
		{
			this.command_queue.put(new Runnable(){
				@Override
				public void run()
				{
					ThreadedGateApp.this.my_app.getDocumentPeople(idx, results);
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * passes the call to the GateApp via a blocking queue
	 */
	@Override
	public void getDocumentRelations(final int idx, final ResultRetriever<List<edge_relation>> results){
		
		try
		{
			this.command_queue.put(new Runnable(){
				@Override
				public void run()
				{
					ThreadedGateApp.this.my_app.getDocumentRelations(idx, results);
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}
	

	/**
	 * passes the call to the GateApp via a blocking queue
	 */
	@Override
	public void getDocumentSubject(final int idx, final ResultRetriever<String> results)
	{
		try
		{
			this.command_queue.put(new Runnable(){
				@Override
				public void run()
				{
					ThreadedGateApp.this.my_app.getDocumentSubject(idx, results);
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * passes the call to the GateApp via a blocking queue
	 */
	@Override
	public void setBlockSize(final int size) {
		try
		{
			this.command_queue.put(new Runnable(){
				@Override
				public void run()
				{
					ThreadedGateApp.this.my_app.setBlockSize(size);
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * yeah hypothetically this could have concurrency issues, but it isn't going to in practice, this value only changes in responce to the UI
	 */
	@Override
	public int getBlockSize() {
		return ThreadedGateApp.this.my_app.getBlockSize();
	}

	/**
	 * sets the tfidf implementation at some point in the future
	 */
	@Override
	public void setTFIDF(final String implementation) {
		try
		{
			this.command_queue.put(new Runnable(){
				@Override
				public void run()
				{
					ThreadedGateApp.this.my_app.setTFIDF(implementation);
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * passes the call to the GateApp via a blocking queue
	 */
	@Override
	public void setResultSize(final int size)
	{
		try
		{
			this.command_queue.put(new Runnable(){
				@Override
				public void run()
				{
					ThreadedGateApp.this.my_app.setResultSize(size);
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * returns the number of results that will be shown
	 * and same speech about concurrency as with getBlockSize
	 */
	@Override
	public int getResultSize()
	{
		return ThreadedGateApp.this.my_app.getResultSize();
	}

}
