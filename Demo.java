import javax.swing.*;
import java.util.*;
import java.awt.*;
import javax.swing.border.*;



class Demo {
    public static void main(String[] args) {

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        JFrame frame = new JFrame("Water Level Observer");
        JLabel display1 = new JLabel("Water Level: ");
        
    
        JLabel display2 = new JLabel("Alarm: OFF");
        JLabel display3 = new JLabel("Splitter: OFF");
        JLabel display4 = new JLabel("SMS: Waiting");

        panel.add(display1);
        panel.add(display2);
        panel.add(display3);
        panel.add(display4);


        frame.add(panel);

        JSlider slider = new JSlider(JSlider.VERTICAL, 0, 100, 0);
        slider.setBorder(new EmptyBorder(10, 50, 10, 10));
        frame.add(BorderLayout.WEST, slider);

        ControlRoom controlRoom = new ControlRoom();

        controlRoom.addWaterLevelObserver(new WaterLevelObserver() {
            public void update(int waterLevel) {
				
				
                display1.setText("Water Level: " + waterLevel);
             	display1.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(10, 50, 10, 10)
			));

                display2.setText(waterLevel > 60 ? "Alarm: ON" : "Alarm: OFF");
                display2.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(10, 50, 10, 10)
			));
 
                display3.setText(waterLevel > 75 ? "Dam: Open" : "Dam: Close");
				display3.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(10, 50, 10, 10)
			));
                
                
                
                SMSSender sms = new SMSSender();
                display4.setText(sms.getMessage (waterLevel));
                display4.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(10, 50, 10, 10)
			));
                
                
            }
        });

        slider.addChangeListener(e -> {
            controlRoom.setWaterLevel(slider.getValue());
        });

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


class SMSSender extends WaterLevelObserver{
	public String getMessage(int waterLevel){
		
		if (waterLevel > 75){
			// return "IMMEDIATE ACTIONS REQUIRED:\nEvacuate Low-Lying Areas:";
			 return "<html>IMMEDIATE ACTIONS REQUIRED:<br>Evacuate Low-Lying Areas:</html>";
	 } else if( waterLevel > 60)
			{return "Water level are Rising";
	}else{
		 return "Normal";
		 }
	}
}
		
	
	
	


/*




class WaterLevelObserver extends JFrame{
	public void update(int waterLevel){
		 //
	}
}  

class Alarm extends WaterLevelObserver{
	private String id;
	Alarm(String id){
		this.id=id;
	}
	public void update(int waterLevel){
		System.out.println(waterLevel>50 ? id+"-->Alarm ON":id+"-->Alarm OFF");
	}
}



class Splitter extends WaterLevelObserver{
	private String id;
	Splitter(String id){
		this.id=id;
	}
	public void update(int waterLevel){
		System.out.println(waterLevel>75 ? id+"-->Splitter ON":id+"-->Splitter OFF");
	}
}

class Display extends WaterLevelObserver{
	private String id;
	Display(String id){this.id=id;}
	public void update(int waterLevel){
		System.out.println(id+"-->Water Level : "+waterLevel);
	}
}





class ControlRoom{
	private WaterLevelObserver[] waterLevelObserverArray=new WaterLevelObserver[0];
	private int waterLevel;
	
	public void addWaterLevelObserver(WaterLevelObserver waterLevelObserver){
		extendArray();
		waterLevelObserverArray[waterLevelObserverArray.length-1]=waterLevelObserver;
	}

	public void setWaterLevel(int waterLevel){
		if(this.waterLevel!=waterLevel){
			this.waterLevel=waterLevel;
			notifyDevices();
		}
	}
	private void notifyDevices(){
		for(WaterLevelObserver ob : waterLevelObserverArray){
			ob.update(waterLevel);
		}
	}
	private void extendArray(){
		WaterLevelObserver[] tempWaterLevelObserverArray=new WaterLevelObserver[waterLevelObserverArray.length+1];
		for (int i = 0; i < waterLevelObserverArray.length; i++){
			tempWaterLevelObserverArray[i]=waterLevelObserverArray[i];
		}
		waterLevelObserverArray=tempWaterLevelObserverArray;	
	}
}





*/








