package Gui;

import Attributes.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ShowEmployees extends JFrame implements ActionListener {
    JButton del;
    JTable Product;
    ShowEmployees(){
        setTitle("Employees");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(700,600);
        setLayout(null);
        setLocationRelativeTo(null);
        ImageIcon mainlogo=new ImageIcon("images\\mainlogo.png");
        setIconImage(mainlogo.getImage());

        JPanel head=new JPanel();
        head.setBounds(0,0,700,100);
        head.setBackground(new Color(44, 62, 80));
        head.setLayout(null);
        JLabel heading= new JLabel("Employee Details");
        heading.setFont(new Font("Futura",Font.BOLD,35));
        heading.setBounds(30,25,400,50);
        heading.setForeground(Color.white);
        head.add(heading);

        String[] col={"No.","Name","Age","Gender","Phone no.","Email"};

        DefaultTableModel Tb=new DefaultTableModel(col,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Product=new JTable(Tb);
        ArrayList<Employee> E= Employee.ReadFile();
        int no=1;
        for(Employee e:E){
            Object[] o={no++,e.getF_name()+" "+e.getL_name(),e.getAge(),e.getGender(),e.getPhone_no(),e.getEmail()};
            Tb.addRow(o);
        }
        Product.setFont(new Font("Futura",Font.PLAIN,14));
        Product.setRowHeight(30);
        TableColumnModel columnModel = Product.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(50);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(150);
        columnModel.getColumn(5).setPreferredWidth(250);



        JScrollPane body=new JScrollPane(Product);
        body.setBounds(1,100,687,400);
        body.setBackground(new Color(234, 250, 241 ));


        JPanel footer=new JPanel();
        footer.setBounds(0,500,700,400);
        footer.setBackground(new Color(93, 109, 126));
        footer.setLayout(null);

        JButton back =new JButton("Back");
        back.setForeground(Color.white);
        back.setBackground(new Color(203, 67, 53));
        back.setBounds(600,20,60,30);
        back.setFocusable(false);
        back.setFont(new Font("Tahoma",Font.PLAIN,12));
        back.addActionListener(En -> {new AdminMenu();dispose();});
        footer.add(back);

        del =new JButton("Fire");
        del.setForeground(Color.white);
        del.setBackground(new Color(230, 126, 34));
        del.setBounds(40,12,90,40);
        del.setFocusable(false);
        del.setFont(new Font("Didot",Font.BOLD,17));
        del.addActionListener(this);
        footer.add(del);


        setVisible(true);
        add(head);
        add(body);
        add(footer);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==del){
            if (Product.getSelectedRow() != -1) {
                int opt=JOptionPane.showConfirmDialog(null,"Are u sure you want to Fire?","Someone's salty",JOptionPane.YES_NO_OPTION);
                if(opt==0) {

                    ArrayList<Employee> P=Employee.ReadFile();
                    P.remove(Product.getSelectedRow());
                    try(ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("Files\\Employee", false))){
                        outStream.writeObject(P);
                    }
                    catch(IOException exp){ }
                    JOptionPane.showMessageDialog(null,"Fired Sucessfully!!","Good luck being lonely",JOptionPane.INFORMATION_MESSAGE);
                    new ShowEmployees();
                    dispose();
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Select an Employee!!", "Dumb Dumb", JOptionPane.ERROR_MESSAGE);
        }
    }
}
