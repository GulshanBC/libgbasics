package ml.gulshanbc.gbasics;

/*
 * Written By: Gulshan Bhagat Chaurasia
 * Date Written: 9 November 2020 @date
 * Date Modified: 20 March 2021 @date
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class GEvent extends java.util.EventObject
{   
    public static final int GEVENT_TYPE_SEARCH_STARTED = 0;
    public static final int GEVENT_TYPE_SEARCH_COMPLETED = 1;
    public static final int GEVENT_TYPE_SET_WORD_COUNT = 2;
    
    public static final int GEVENT_TYPE_ALL_WORDS_WINDOW_WORD_CLICKED = 3;
    public static final int GEVENT_TYPE_SERVER_STARTED = 4;
    public static final int GEVENT_TYPE_SERVER_IP_CHANGED = 5;
    
    public static final int GEVENT_TYPE_DOWNLOAD_INFO_LINE = 6;
    public static final int GEVENT_TYPE_DOWNLOAD_PROGRESS_INFO_LINE = 7;
    public static final int GEVENT_TYPE_PLAYBACK_AV_INFO_LINE = 8;
    
    public static final int GEVENT_TYPE_PAGE_CHANGED = 9;
    public static final int GEVENT_TYPE_PAGE_COMPLETED = 10;
    public static final int GEVENT_TYPE_GLISTITEM_CLICKED = 12;
    public static final int GEVENT_TYPE_ITEM_ADDED_TO_GLISTDATAMODEL = 13;
    public static final int GEVENT_TYPE_ITEM_REMOVED_FROM_GLISTDATAMODEL =14;
    public static final int GEVENT_TYPE_GLISTITEM_REMOVED = 15;
    public static final int GEVENT_TYPE_GLISTITEM_DOUBLE_CLICKED = 16;
    public static final int GEVENT_TYPE_HTML_SYNTAX_HIGHLIGHTING_THREAD_STOPPED_ON_REQUEST = 17;
    public static final int GEVENT_TYPE_HTML_SYNTAX_HIGHLIGHTING_THREAD_COMPLETED_SUCCESSFULLY = 18;
    
    public static final int GEVENT_TYPE_SCROLL_TEXT = 20;
    public static final int GEVENT_TYPE_TEXT_GAP = 21;
    public static final int GEVENT_TYPE_TEXT_SIZE = 22;
    public static final int GEVENT_TYPE_LED_TYPE = 23;
    public static final int GEVENT_TYPE_PAINT_TYPE = 24;
    public static final int GEVENT_TYPE_GRADIENT_TYPE = 25;
    public static final int GEVENT_TYPE_COLOR_1 = 26;
    public static final int GEVENT_TYPE_COLOR_2 = 27;
    public static final int GEVENT_TYPE_SET_RANDOM_COLOR_DELAY = 28;
    public static final int GEVENT_TYPE_SCROLL_HORIZONTAL = 29;
    public static final int GEVENT_TYPE_SCROLL_VERTICAL = 30;
    public static final int GEVENT_TYPE_SCROLL_DELAY = 31;
    public static final int GEVENT_TYPE_BOARD_COLOR = 32;
    public static final int GEVENT_TYPE_AUTO_RESIZE = 33;
    public static final int GEVENT_TYPE_ENABLE_RANDOM_COLOR = 34;
    public static final int GEVENT_TYPE_PLACE_DISPLAY2_INSIDE_WINDOW = 35;
    
//    public static final int GEVENT_TYPE_TASK_MESSAGE = 3;
//    public static final int GEVENT_TYPE_ITEM_REMOVED_FROM_GLISTDATAMODEL = 4;
//    public static final int GEVENT_TYPE_GLISTITEM_REMOVED = 5;
//    public static final int GEVENT_TYPE_GLISTITEM_DOUBLE_CLICKED = 6;
//    public static final int GEVENT_TYPE_HTML_SYNTAX_HIGHLIGHTING_THREAD_STOPPED_ON_REQUEST = 7;
//    public static final int GEVENT_TYPE_HTML_SYNTAX_HIGHLIGHTING_THREAD_COMPLETED_SUCCESSFULLY = 8;
//    public static final int GEVENT_TYPE_SCROLL_HORIZONTAL = 9;
//    public static final int GEVENT_TYPE_SCROLL_VERTICAL = 10;
//    public static final int GEVENT_TYPE_SCROLL_DELAY = 11;
//    public static final int GEVENT_TYPE_BOARD_COLOR = 12;
//    public static final int GEVENT_TYPE_AUTO_RESIZE = 13;
//    public static final int GEVENT_TYPE_ENABLE_RANDOM_COLOR = 14;
//    
    
//    public static final int GEVENT_TYPE_PLACE_DISPLAY2_INSIDE_WINDOW = 114;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3951065219308213943L;	
	private int intData = -1;
	private String stringData = null;
	private String[] stringArrayData = null;//even vectors can be passed in events        
        private Object objectData = null;
        private Object[] objectArrayData = null;//even vectors can be passed in events
        private int eventType = -1;
        
        public GEvent(){
            this(null,-1,"");
        }
	
	public GEvent(Object source)
	{
		this(source , -1 , -1, "" );
	}
        
        public GEvent(int eventType)
	{
		this(new Object() , eventType , -1, "" );
	}
			
	public GEvent(Object source, String _stringData)
	{			
		this(source , -1 , -1,_stringData );
	}
	
	public GEvent(Object source , String[] _stringArrayData)
	{
		super(source);		
		stringArrayData = _stringArrayData;
	}
        
        public GEvent(Object source, Object _objectData)
	{
		super(source);		
		objectData = _objectData;
	}
	
	public GEvent(Object source , Object[] _objectArrayData)
	{
		super(source);		
		objectArrayData = _objectArrayData;
	}
        
        public GEvent(Object source, int eventType)
	{
		this(source , eventType , -1,null );
	}
	
        public GEvent(Object source, int eventType,int intData)
	{
		this(source , eventType , intData,null );
	}
        
        public GEvent(Object source,int eventType , String _stringData)
	{
		this(source,eventType,-1,_stringData);				
	}
	
	public GEvent(Object source, int eventType,int _intData , String _stringData)
	{
		super(source); 
                this.eventType = eventType;
		intData = _intData;
		stringData = _stringData;
	}
		
	public GEvent(Object source , int eventType, String[] _stringArrayData)
	{
		super(source);
                this.eventType = eventType;
		stringArrayData = _stringArrayData;
	}
        
        public GEvent(Object source, int eventType, Object _objectData)
	{
		super(source);	
                this.eventType = eventType;
		objectData = _objectData;
	}
	
	public GEvent(Object source , int eventType, Object[] _objectArrayData)
	{
		super(source);	
                this.eventType = eventType;
		objectArrayData = _objectArrayData;
	}
        
        public void setEventType(int eventType)
        {
            this.eventType = eventType;
        }
        
        public int getEventType()
        {
            return eventType;
        }
	
	public int getInt()
	{
		return intData;
	}
	
	public void setInt( int _intData )
	{
		intData = _intData;
	}
	
	public String getString()
	{
		return stringData;
	}
	
	public void setString(String _string)
	{
		stringData = _string;
	}
	
	public String[] getStringArray()
	{
		return stringArrayData;
	}
	
	public void setStringArray(String[] stringArray)
	{
		stringArrayData = stringArray;
	}
        
        public String getObject()
	{
		return stringData;
	}
	
	public void setObject(Object object)
	{
		objectData = object;
	}
	
	public Object[] getObjectArray()
	{
		return objectArrayData;
	}
	
	public void setObjectArray(Object[] objectArray)
	{
		objectArrayData = objectArray;
	}
}

