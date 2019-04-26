package searchengine.main;

import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.*;
import searchengine.search.SearchDriver;

@SuppressWarnings("serial")
public class Server extends JFrame implements ActionListener 
{
	ServerSocket ssc;
	Socket client;
	String s;

        public static JTextArea ja=new JTextArea(6,20);
	JButton jb;
	JPanel jp,jp1;
	JLabel jl;
	JComboBox jcb=new JComboBox();
	public static String str;
	Server() throws IOException
	{
		setTitle("Search Engine Server");
		jl=new JLabel("MINI GOOGLE SEARCH ENGINE");
		jl.setFont(new Font("Verdana",Font.BOLD,30));
		jl.setForeground(Color.blue);
		jl.setBackground(Color.gray);
		jb=new JButton("Stop");
		jp=new JPanel();
		jp1=new JPanel();
		jp.setLayout(new BorderLayout());
		Container c=getContentPane();
		c.setLayout(new BorderLayout());
		jp.add(jl);
		jp1.add(jb);
		jp.add(jl,BorderLayout.NORTH);
		jp.add(jp1,BorderLayout.SOUTH);
		c.add(jp,BorderLayout.NORTH);
		c.add(ja,BorderLayout.CENTER);
		jb.addActionListener(this);
		setSize(750,550);
		jp.setBackground(new Color(230,100,30));
                ssc=new ServerSocket(4002,10);
		s=null;
		ja.setText("");
		ja.setBackground(Color.lightGray);
		ja.append("Connection Established :");
		ja.append(str);
	}
        public void actionPerformed(ActionEvent e)
	{
		
		System.out.println("str is "+str);
		
		if(e.getSource()==jb)
		{
			System.exit(0);
		}


	}
	public void get_data() throws IOException
	{
		client=ssc.accept();
		SearchDriver sd=new SearchDriver();
		BufferedReader ois=new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter oos=new PrintWriter(client.getOutputStream(),true);
		String st=ois.readLine();
		StringTokenizer ste=new StringTokenizer(st);
		String str1=ste.nextToken("\t");
		String str2=ste.nextToken("\t");
		if(str2.equals("null"))
			str2=null;
		String str3=ste.nextToken("\t");
		if(str3.equals("null"))
			str3=null;
		String sr=sd.Search(str1,str2,str3,ste.nextToken());
		ste=new StringTokenizer(sr);
		int i=0;
		while(ste.hasMoreTokens())
		{
			oos.println(ste.nextToken("\n"));
			i++;
		}
		oos.println("\n\nsearch complited:"+i+"links found");
		oos.println("end");
	}
	
	
	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws IOException
	{
		while(true)
		{
			Server sc1=new Server();
			sc1.setDefaultCloseOperation(1);
			sc1.setVisible(true);
			sc1.show();
			sc1.get_data();
		}
        
	}

}
