package ml.gulshanbc.gbasics;

/**
 * Written By: Gulshan Bhagat Chaurasia
 * Date Written: 7th July,2019
 * Last Modified: 30th March,2020
 * @author gulshan
 *
 */
public class GTTSThread extends Thread
{
	protected Process pico2waveProcess = null;
	protected Process playProcess = null;
	
	
	String[] commandToPico2wave;
	String[] commandToPlay;
	int offset = 0;
	
	boolean flag_end = false;		
	
	/**
	 * offset is equal to line number of the line that contains the cursor
	 * @param text_to_read
	 * @param offset
	 */
	public GTTSThread(String text_to_read)
	{
		this(text_to_read,0, 30, -20, 1);
	}
	
	/**
	 * offset is equal to line number of the line that contains the cursor
	 * @param text_to_read
	 * @param offset
	 */
	public GTTSThread(String text_to_read,int offset)
	{
		this(text_to_read,offset, 30, -20, 1);
	}
	
	/**
	 * offset is equal to line number of the line that contains the cursor
	 * @param text_to_read
	 * @param offset
	 */
	public GTTSThread(String text_to_read,int offset, int treble, int bass, int tempo)
	{
		try 
		{			
			String filename_in_user_home_path = System.getProperty("user.home")+"/gtts.wav";
			
			String[] command_to_pico = {"pico2wave","-w","gtts.wav", "-l", "en-US","text to be spoken goes here"};
			String[] command_to_play = {"play","-q", "gtts.wav","treble", "30", "bass", "-20" ,"tempo" , "1"};
			
			command_to_pico[2] = filename_in_user_home_path;
			//RadioButton selectedrb  = (RadioButton)prefs_language_togglegroup.getSelectedToggle();
			//command_to_pico[4] = selectedrb.getUserData().toString();
			command_to_pico[5] = text_to_read;		
			
			command_to_play[2] = filename_in_user_home_path;
														
			//int value = (int)slider_treble.getValue();
			command_to_play[4]=String.valueOf(treble);
			
			//value = (int)slider_bass.getValue();
			command_to_play[6]=String.valueOf(bass);
			
			//Float fvalue = (float)slider_speed.getValue();
			command_to_play[8]=String.valueOf(tempo);
			
			commandToPico2wave = command_to_pico;
			commandToPlay = command_to_play;
			setOffset(offset);
			this.start();
		}
		catch(Exception e) {System.out.println(e.getMessage());}
	}

	@Override
	public void run() {
		try {
			if( commandToPico2wave[5].length() > 500)
			{
				String current_text_to_read = "";
				String text_to_read = commandToPico2wave[5];
				
				String[] lines = text_to_read.split("\n");
				for(int i = 0 ; i< lines.length ; i+=10)
				{
					current_text_to_read = "";
					for(int j = i ; j < i+10; j++)
					{
						try {//using try catch block to prevent ArrayIndexOufOfBound exception in last chunk
						current_text_to_read += lines[j];
						}catch(Exception e) {System.out.println(e.getMessage());}
					}
					
					commandToPico2wave[5] = current_text_to_read;				
					
					pico2waveProcess = Runtime.getRuntime().exec(commandToPico2wave);
					
					while( pico2waveProcess.isAlive() )
					{
						System.out.println("pico2waveProcess is running!!");
						//callbackInterface.showInStatusbar("pico2wave process is running!!");
						sleep(1000);			
					}
					
					playProcess = Runtime.getRuntime().exec(commandToPlay);	
					
					while( playProcess.isAlive() )
					{
						System.out.println("playProcess is running!!");
						//callbackInterface.showInStatusbar("play process is running!!" + " | lines being read: "+(i+1+offset)+"-"+(i+10+offset));
						sleep(1000);			
					}
					if(flag_end) break;
				}
				//callbackInterface.done();
			}
			else 
			{				
				pico2waveProcess = Runtime.getRuntime().exec(commandToPico2wave);
				
				while( pico2waveProcess.isAlive() )
				{
					System.out.println("pico2waveProcess is running!!");
					//callbackInterface.showInStatusbar("pico2wave process is running!!");
					sleep(1000);			
				}
				
				playProcess = Runtime.getRuntime().exec(commandToPlay);	
				
				while( playProcess.isAlive() )
				{
					System.out.println("playProcess is running!!");
					//callbackInterface.showInStatusbar("play process is running!!");
					sleep(1000);			
				}
				
				//callbackInterface.done();
			}
			
		}
		catch(Exception e) {System.out.println(e.getMessage());}
	}

	public void end()
	{
		try {	
			flag_end = true;
			
			if(pico2waveProcess.isAlive()) pico2waveProcess.destroyForcibly();
			if(playProcess.isAlive()) playProcess.destroyForcibly();
			}catch(Exception e) {System.out.println(e.getMessage());}
	}
	
	public void setOffset(int offset)
	{
		this.offset = offset;
	}
}
