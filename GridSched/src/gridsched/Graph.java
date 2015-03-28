package gridsched;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Random;
import javax.swing.*;

public class Graph implements Runnable {
    graphPanel graphPanel = new graphPanel();
    long delay = 20*1000;

    public Graph()
    {
        Thread thread = new Thread(this);
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start();
    }

    public void run() {
        while(true) {
            graphPanel.setData();
            try {
                Thread.sleep(delay);
            } catch(InterruptedException e) {
                break;
            }
        }
    }



    private JPanel getContent() {
        return graphPanel;
    }

    public static void main() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(540,400);
        f.setMaximumSize(new Dimension(540,400));
        f.setLocation(200,200);
        f.add(new Graph().getContent());
        f.setVisible(true);

    }
}

class graphPanel extends JPanel {

    final int PAD = 20;
    JLabel l1 = new JLabel();
    JLabel l12 = new JLabel();
    JLabel l13 = new JLabel();


    public void setData() {

        this.setLayout(new BorderLayout());
        //System.out.printf("data = %s%n", java.util.Arrays.toString(gridsched.));
        l1.setText("     0"+"              "+gridsched.Grid_AVSN.algos[0][1]+"                            "+gridsched.Grid_AVSN.algos[1][1]+"                              "+gridsched.Grid_AVSN.algos[2][1]+"                       "+gridsched.Grid_AVSN.algos[3][1]);
        l12.setText("Secs");
        //l13.setText("  50");
        add(l1,BorderLayout.SOUTH);
        add(l12,BorderLayout.NORTH);
        //add(l13,BorderLayout.WEST);
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        System.out.println("w and h"+w + " "+h );
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        double xInc = ((double)(w - 2*PAD)/4-30);
        //double scale = (double)(h - 2*PAD)/maxValue;
        Path2D.Double path = new Path2D.Double();
        // Draw data.
        double x = 65;
        double y = h - PAD;
        for(int i=0;i<4;i++,x=x+xInc)
        {
            if(gridsched.Grid_AVSN.algos[i][0].equalsIgnoreCase("1"))
            {
                g2.setPaint(Color.blue);
                for(int j=0;j<20;j++,x++)
                {
                path.moveTo(x, y);
                path.lineTo(x,y-(Long.parseLong(gridsched.Grid_AVSN.algos[i][2])));
                }
                g2.drawString(gridsched.Grid_AVSN.algos[i][2]+"secs", (int)(x-20),(int)(y-(Long.parseLong(gridsched.Grid_AVSN.algos[i][2]))-20 ));
                g2.setPaint(new Color(120,220,50));
                g2.fill(path);
                g2.draw(path);
                y=h-PAD;
            }
        }




    }
}