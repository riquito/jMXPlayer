package test.Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import src.Model.GraphicInstanceGroup;
import src.Model.MXData;
import src.Model.Voice;


public class MXDataTest {
   /**
    * Purpose: make MXData
    * Input: 
    * Expected: not Null
    *       return SUCCESS
    *       mXData -> not null
    *       
    *
    */
   @Test
   public void testMXData() {
      MXData mXData = new MXData();

      assertNotNull(mXData);
   }
   
   /**
    * Purpose: make spine label 
    * Input: 
    * Expected: not Null
    *       return SUCCESS
    *       mXData -> not null
    *
    */
   @Test
   public void testaddGroup() {
      GraphicInstanceGroup group = null;
      MXData mXData = new MXData();
      mXData.addGroup(group);
      assertNotNull(mXData);
   }
   
   /**
    * Purpose: get Audio Clip Dictionary
    * Input: 
    * Expected: not Null
    *       return SUCCESS
    *       mXData -> not null
    *
    */
   @Test
   public void testgetAudioClipDictionary() {
      MXData mXData = new MXData();
      mXData.getAudioClipDictionary();
      assertNotNull(mXData);
   }
   
   /**
    * Purpose: get Voices
    * Input: 
    * Expected: not Null
    *       return SUCCESS
    *       mXData -> not null
    *
    */
   @Test
   public void testgetVoices() {
      MXData mXData = new MXData();
      mXData.getVoices();
      assertNotNull(mXData);
   }
   
   /**
    * Purpose: get Spine2 voice
    * Input: 
    * Expected: not Null
    *       return SUCCESS
    *       mXData -> not null
    *
    */
   @Test
   public void testgetSpine2voice() {
      MXData mXData = new MXData();
      mXData.getSpine2voice();
      assertNotNull(mXData);
   }
   
   /**
    * Purpose: setBaseDirectory getBaseDirectory
    * Input: 
    * Expected: 
    *       return SUCCESS
    *       setBaseDirectory(test) 
    *      getBaseDirectory() == test
    */
   @Test
   public void testgetsetBaseDirectory() {
      String test = "Test";
      MXData mXData = new MXData();
      mXData.setBaseDirectory(test);
      assertEquals(test, mXData.getBaseDirectory());
   }
   
   /**
    * Purpose: setWorkTitle getWorkTitle
    * Input: 
    * Expected: 
    *       return SUCCESS
    *       setWorkTitle(test) 
    *      getWorkTitle() == test
    */
   @Test
   public void testgetsetWorkTitle() {
      String test = "Test";
      MXData mXData = new MXData();
      mXData.setWorkTitle(test);
      assertEquals(test, mXData.getWorkTitle());
   }
   
   /**
    * Purpose: setMovementTitle getMovementTitle 
    * Input: 
    * Expected: 
    *       return SUCCESS
    *       setMovementTitle(test) 
    *      getMovementTitle() == test
    */
   @Test
   public void testgetsetMovementTitle() {
      String test = "Test";
      MXData mXData = new MXData();
      mXData.setMovementTitle(test);
      assertEquals(test, mXData.getMovementTitle());
   }
   
   /**
    * Purpose: add Voice
    * Input: 
    * Expected: 
    *       return SUCCESS
    *       mXData -> not null
    *      
    */
   @Test
   public void testaddVoice() {
      String test = "Test";
      Voice voice=null;
      MXData mXData = new MXData();
      mXData.addVoice(test, voice);
      assertNotNull(mXData);
   }
   
}