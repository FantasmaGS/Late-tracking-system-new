import java.awt.*;
import javax.swing.JFrame;//This can make the "X" button work, (close the window function)
import java.util.Calendar;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class LTS extends JFrame {
   static String fileName = "records.txt";             // fileNAme = "records.txt"
   final int MAX = 20;                    // Set MAX number of records
   String rows [] = new String[MAX];      // Store max 20 records
   String info[][] = new String[MAX][6];  // Store records in 2d array
   ReadData rd = new ReadData();          // Instantiate the class ReadData
   Records re = new Records();           	// Instantiate the class Records
   Sorter s = new Sorter();
   SearchInput si = new SearchInput();
   SearchInput2 si2 = new SearchInput2();
   KeyInput ki = new KeyInput();
   NewRecord nr = new NewRecord();
   Output ou = new Output();
   
   String nameArray [ ] = new String[10];
   
   private int centerX = 120;
   private int centerY = 250;
   private int radius = 100;
   
   public LTS() {//constructor to prepare window size and menubar
      super("My Window");
      resize(700, 500);//Window Size
      makeMenus();
      show();
   }//end constructor

   //Painting
   public void paint(Graphics g) {//insert text and geometric shapes
  
      Graphics2D g2d = (Graphics2D) g;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      
      GradientPaint gp = new GradientPaint(0, 0, Color.WHITE, getWidth(), getHeight(), Color.WHITE);
      g2d.setPaint(gp);
      g2d.fillRect(0, 0, getWidth(), getHeight());
      
      g2d.setFont(new Font("Helvetica", Font.BOLD, 24)); // Change font, size
      g2d.setColor(Color.blue);// Change Color
      g2d.drawString("Late Tracking System.", 160, 100);//Print Text
      g2d.drawRect(230,180,100,50);  //Draw a rectabkge - x,y,width,height
      g2d.drawOval(280,200,120,80); //Draw a oval - x,x,width,height
      g2d.drawString("By Michael and Shouveek", 300, 400);//Print Text
      g2d.drawString("LTS", 250, 210);//Print Text
      
      // Draw clock numbers
      g2d.setFont(new Font("Arial", Font.BOLD, 18));
      g2d.setColor(Color.BLACK);
      for (int i = 1; i <= 12; i++) {
         double angle = Math.PI / 6 * (i - 3);
         int x = (int) (centerX + Math.cos(angle) * radius * 0.8);
         int y = (int) (centerY + Math.sin(angle) * radius * 0.8);
         g2d.drawString(Integer.toString(i), x, y);
      }
      
      // Draw clock hands
      Calendar calendar = Calendar.getInstance();
      int hour = calendar.get(Calendar.HOUR);
      int minute = calendar.get(Calendar.MINUTE);
      int second = calendar.get(Calendar.SECOND);
      int angleHour = (int) (hour * 30 + minute * 0.5 - 90);
      int angleMinute = (int) (minute * 6 - 90);
      int angleSecond = (int) (second * 6 - 90);
      
      // Draw hour hand
      g2d.setColor(Color.BLACK);
      drawHand(g2d, angleHour, (int) (radius * 0.5), 6);
      
      // Draw minute hand
      g2d.setColor(Color.BLACK);
      drawHand(g2d, angleMinute, (int) (radius * 0.7), 4);
      
      // Draw second hand
      g2d.setColor(Color.BLACK);
      drawHand(g2d, angleSecond, (int) (radius * 0.9), 2);
   
   }
   
   private void drawHand(Graphics2D g2d, int angle, int length, int thickness) {
      g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
      int x = (int) (centerX + Math.cos(Math.toRadians(angle)) * length);
      int y = (int) (centerY + Math.sin(Math.toRadians(angle)) * length);
      g2d.drawLine(centerX, centerY, x, y);
   }
   //End Painting

   private void makeMenus() { // instantiate menubar
      MenuBar mb = new MenuBar();
      
      Menu data = new Menu ("Data"); //Create menu1
      
      Menu delete = new Menu ("Delete");//Create sub menu for Delete
      delete.add(new MenuItem("by Names"));
      delete.add(new MenuItem("by IDs"));
      delete.add(new MenuItem("by Homeforms"));
      delete.add(new MenuItem("by Periods"));
      
      data.add(new MenuItem("Load"));
      data.addSeparator();//It adds a line between buttons
      data.add(new MenuItem("List"));
      data.addSeparator();
      data.add(new MenuItem("New"));
      data.addSeparator();
      data.add(delete);// SubMenu
      data.addSeparator();
      data.add(new MenuItem("Print"));
      data.addSeparator();
      data.add(new MenuItem("Quit"));
       
      Menu sort = new Menu ("Sort"); //Create menu2
      sort.add(new MenuItem("Names"));
      sort.addSeparator();
      sort.add(new MenuItem("IDs"));
      sort.addSeparator();
      sort.add(new MenuItem("Homeforms"));
      sort.addSeparator();
      sort.add(new MenuItem("Periods"));
       
      Menu find = new Menu ("Find"); //Create menu3
      find.add(new MenuItem("By Names"));
      find.addSeparator();
      find.add(new MenuItem("By IDs"));
      find.addSeparator();
      find.add(new MenuItem("By Homeforms"));
      find.addSeparator();
      find.add(new MenuItem("By Periods"));
       
      Menu info = new Menu ("Info"); //Create menu4
      info.add(new MenuItem("Help"));
       
      mb.add(data); //add Data to menubar
      mb.add(sort); //add Sort to menubar
      mb.add(find); //add Find to menubar
      mb.add(info); //add Info to menubar
      setMenuBar(mb);
   } // end makeMenus

   public boolean action(Event evt, Object obj) { // Activate menuitem
      if(evt.target instanceof MenuItem) {
         String label = (String) obj;
      //Under Data in the Menubar
      if(label.equals("Quit"))
         System.exit(0); //quit window
      else if (label.equals("Load")) //Activate menuitem
         {
  	         System.out.println("Load coming up!"); //menuItem1_1 works
            rows = rd.readFile(fileName, MAX);
            info = re.getRecords(rows); 
         }
      else if (label.equals("List"))
         {
            System.out.println("List coming up!");
            for (int i = 0; i < info.length; i++){
               for (int j = 0; j < 6; j++){
                  System.out.println(info[i][j]);
               }
            System.out.println(" ");
            }
         }
      else if (label.equals("New"))
         {
            System.out.println("New coming up!");
            String newData = KeyInput.entries();
            NewRecord.addRecord("records.txt", newData);
         }
      // SUB MENU
      else if (label.equals("by Names"))
         {
            System.out.println("by Names to delete coming up!");
            si2.keyInput(fileName, info, 0);// Key in input and conduct a search to delete
         }
      else if (label.equals("by IDs"))
         {
            System.out.println("by IDs to delete coming up!");
            si2.keyInput(fileName, info, 1);
         }
      else if (label.equals("by Homeforms"))
         {
            System.out.println("by Homeforms to delete coming up!");
            si2.keyInput(fileName, info, 2);          
         }
      else if (label.equals("by Periods"))
         {
            System.out.println("by Periods to delete coming up!");
            si2.keyInput(fileName, info, 5);
         }
      // END OF SUB MENU
      else if (label.equals("Print"))
         {
            System.out.println("Print coming up!");
            ou.keyin(info);
         }
         
      //Under Sort in the Menubar
      else if (label.equals("Names"))
         {
            System.out.println("Names coming up!");
            info = s.sort(info, 0); 
            for (int i = 0; i < info.length; i++){
               for (int j = 0; j < 6; j++){
                  System.out.println(info[i][j]);
               }
            System.out.println(" ");
            }
         }
      else if (label.equals("IDs"))
         {
            System.out.println("IDs coming up!");
            info = s.sort(info, 1); 
            for (int i = 0; i < info.length; i++){
               for (int j = 0; j < 6; j++){
                  System.out.println(info[i][j]);
               }
            System.out.println(" ");
            }
         }
      else if (label.equals("Homeforms"))
         {
            System.out.println("Homeforms coming up!");
            info = s.sort(info, 2); 
            for (int i = 0; i < info.length; i++){
               for (int j = 0; j < 6; j++){
                  System.out.println(info[i][j]);
               }
            System.out.println(" ");
            }
         }
      else if (label.equals("Periods"))
         {
            System.out.println("Periods coming up!");
            info = s.sort(info, 5); 
            for (int i = 0; i < info.length; i++){
               for (int j = 0; j < 6; j++){
                  System.out.println(info[i][j]);
               }
            System.out.println(" ");
            }
         }
      
      //Under Find in the Menubar
      else if (label.equals("By Names"))
         {
            System.out.println("By Names coming up!");
            si.keyInput(fileName, info, 0); // Key in input and conduct a search
         }
      else if (label.equals("By IDs"))
         {
            System.out.println("By IDs coming up!");
            si.keyInput(fileName, info, 1);
         }
      else if (label.equals("By Homeforms"))
         {
            System.out.println("By Homeforms coming up!");
            si.keyInput(fileName, info, 2);         
         }
      else if (label.equals("By Periods"))
         {
            System.out.println("By Periods coming up!");
            si.keyInput(fileName, info, 5);
         }
      
      //Under Info in the Menubar
      else if (label.equals("Help"))
         {
            System.out.println("Help coming up!");
            System.out.println("\n(1) Select Load from Data menu first.");
        	   System.out.println("(2) Select any menu Item.");
        	   System.out.println("(3) Select List from Data to view results.\n");
         }
      }
      return true;
   } // end action

   public static void main (String str[]) {
      new LTS(); // Instantiate LTS class
      fileName = "records.txt";   //Does it really work?
   } //  end main
} // end class