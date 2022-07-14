/*
 * Written By: Gulshan Bhagat Chaurasia
 * Date Written: 9 November 2020 @date
 * Date Modified: 10 November 2020 @date
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.gulshanbc.gbasics;

import java.util.ArrayList;


/**
 *
 * @author gulshan
 */
public class GEventDispatcher {
    protected static ArrayList<GEventListener> listeners = new ArrayList<GEventListener>();
	
    public static void registerGEventListener(GEventListener mcl)
    {
        listeners.add(mcl);
    }

    public static void unregisterGEventListener(GEventListener mcl)
    {
        listeners.remove(mcl);
    }

    public static void fireGEvent(GEvent e)
    {
        //System.out.println("GEvent fired");
        for( int i=0; i < listeners.size(); i++ )
        {
            ((GEventListener)listeners.get(i)).eventOccured(e);
        }
    } 
    
//    public static void fireGEventCoelease(GEvent e, long waitTime){
//        if(threadUsedForMovingDisplay!=null && threadUsedForMovingDisplay.isAlive()) flagStopThreadIfPossible = true;
//        threadUsedForMovingDisplay = new Thread(){                                
//            public void run(){
//                try{
//                    Thread.sleep(500);
//                }catch(Exception e){}
//                if(flagStopThreadIfPossible) {
//                    flagStopThreadIfPossible = false;
//                    return;
//                }
//            }
//        };
//        threadUsedForMovingDisplay.start();
//    }
}
