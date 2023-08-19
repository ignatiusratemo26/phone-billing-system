//importing the required libraries, including time and GUI
import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TimeAndBilling extends JFrame {
    //Declaration of variables to be used in the program including the buttons that appear on the user interface
    private JButton startButton, stopButton, thisNet, otherNet;
    private JLabel timeLabel;
    private long startTime;
    private int inThisnet, externalnet, localtime;
    private double charge,additionalRate=0.16;

    public TimeAndBilling() {            
        //defining the dimensions and general appearance of the java frame(User interface) of the program
        setTitle("OLIVE-TEL");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //creating the main panel of the java frame and defining its appearance
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10,20));
        mainPanel.setBackground(Color.WHITE);
        JTextField textField = new JTextField();
        textField.setText("Olive-Tel Network");
        Font font= new Font("Helvetica", Font.BOLD,12);
        textField.setFont( font);
        
        //instantiating the buttons and their attributes
        thisNet = new JButton("Call this network");
        thisNet.setBackground(Color.cyan);
        otherNet = new JButton("Call another network");
        otherNet.setBackground(Color.cyan);
        startButton = new JButton("Start call");
        stopButton = new JButton("End call");
        startButton.setEnabled(false);
        startButton.setBackground(Color.cyan);
        stopButton.setEnabled(false);
        stopButton.setBackground(Color.red);
        //creating another panel that will hold all the buttons in the program
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        //Adding all the created buttons onto the button panel
        buttonPanel.add(thisNet);
        buttonPanel.add(otherNet);
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(textField);
        //adding the button panel in the main panel of the program frame
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        timeLabel = new JLabel("Choose a network first.\n Then Click start to initiate a call.");
        mainPanel.add(timeLabel, BorderLayout.NORTH);
        
        //creating an action listener for the thisNet button
        thisNet.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                startButton.setEnabled(true);
                otherNet.setEnabled(false);
                inThisnet=1;                
            }
        });
        //creating an action listener for the otherNet button
        otherNet.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                startButton.setEnabled(true);
                thisNet.setEnabled(false);
                externalnet=1;}
        });
        //adding an action listener to the startButton and the code to be executed once it is clicked
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startTime = System.currentTimeMillis();
                timeLabel.setText("Call in progress...");
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                LocalTime currenttime=LocalTime.now();
                localtime=currenttime.getHour();
            }
        });
        //adding an action listener to the stopButton and the code to be executed once it is clicked
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long stopTime = System.currentTimeMillis();
                double elapsedTime = (stopTime - startTime) / 1000.0;
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                timeLabel.setText("Call ENDED");
                //calculation of charges for calls within the network
                if(inThisnet==1){
                    //charges for calls betweeen 6am and 6pm are Ksh. 3.00 per minute
                    if(localtime>=6 && localtime<18)
                        charge = elapsedTime*1/15;
                    //charges for calls from 6pm to 6 am are Ksh. 4.00 per minute 
                    else
                        charge = elapsedTime*1/20;                       
                }
                //for calls to other networks, the charges are Ksh. 5.00 per minute
                else
                    charge = elapsedTime*1/12;
                
                //for call durations that exceed 2 mins, an additional rate of 16% of the call charge is imposed 
                if(elapsedTime >120)
                   charge += charge* additionalRate;
                JOptionPane.showMessageDialog(null, "Call duration is: "+elapsedTime+"s \nCall charge: Ksh. "+ String.format("%.2f",charge)+"\n");
            }
        });        
        add(mainPanel);
        setVisible(true);
    }        
    }