/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Dados.vclientes;
import Dados.vprodutos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hugo
 */
public class fclientes {
    private conexao mysql=new conexao();
    private Connection cn=mysql.conectar();
    private String sSQL="";
     private String sSQL2="";
    public Integer totalregistros;
    
    
    public DefaultTableModel mostrar(String buscar){
        
    DefaultTableModel modelo;
    String[] titulos = {"ID", "Nome", "Nome Pai", "Nome Mãe", "Documento", "Numero DOC", "Endereço", "Telefone", "Email", "Código"};
    String[] registro = new String[10];
    totalregistros = 0;
    
    modelo = new DefaultTableModel(null, titulos);
   sSQL = "select p.id_pessoa,p.nome_pessoa,p.nome_pai,p.nome_mae,p.tipo_documento,p.num_documento,"
                + "p.endereco,p.telefone,p.email,c.codigo_cliente from tb_pessoas p inner join tb_clientes c "
                + "on p.id_pessoa=c.id_pessoa where num_documento like '%"
                + buscar + "%' order by id_pessoa desc";
    
    try{
        Statement st = cn.createStatement();
        ResultSet rs=st.executeQuery(sSQL);
        while(rs.next()){
               registro [0]=rs.getString("id_pessoa");
               registro [1]=rs.getString("nome_pessoa");
               registro [2]=rs.getString("nome_pai");
               registro [3]=rs.getString("nome_mae");
               registro [4]=rs.getString("tipo_documento");
               registro [5]=rs.getString("num_documento");
               registro [6]=rs.getString("endereco");
               registro [7]=rs.getString("telefone");
               registro [8]=rs.getString("email");
               registro [9]=rs.getString("codigo_cliente");
               
                
               totalregistros = totalregistros +1;
               modelo.addRow(registro);
        }
        return modelo;
    }catch (Exception e){
        JOptionPane.showConfirmDialog(null, e);
        return null;
    }
    
    }
    
    public boolean inserir (vclientes dts){
        sSQL = "insert into tb_pessoas (nome_pessoa, nome_pai, nome_mae, tipo_documento, num_documento, endereco, telefone, email)" +
                "values(?,?,?,?,?,?,?,?)";
        sSQL2 = "insert into tb_clientes (id_pessoa, codigo_cliente)" +
                "values((select id_pessoa from tb_pessoas order by id_pessoa desc limit 1),?)";
        try {
            
            PreparedStatement pst=cn.prepareStatement(sSQL);
            PreparedStatement pst2=cn.prepareStatement(sSQL2);
            
            pst.setString(1, dts.getNome());
            pst.setString(2, dts.getNome_pai());
            pst.setString(3, dts.getNome_mae());
            pst.setString(4, dts.getTipo_documento());
            pst.setString(5, dts.getNum_documento());
            pst.setString(6, dts.getEndereco());
            pst.setString(7, dts.getTelefone());
            pst.setString(8, dts.getEmail());
            
            pst2.setString(1, dts.getCodigo_cliente());
            
            int n=pst.executeUpdate();
            if (n!=0){
                
                int n2=pst2.executeUpdate();
                if (n2!=0){
                return true;
                }
                
                else{
                return false;
            }
                
            }else{
                return false;
            }
            
        }catch (Exception e){
             JOptionPane.showConfirmDialog(null, e);
             return false;
        }
    }
    
    public boolean editar (vclientes dts){
        
        sSQL="update tb_produtos set nome_pessoa=?, nome_pai=?, nome_mae=?, tipo_documento=?, num_documento=?, endereco=?, telefone=?, email=?" +
                "where id_pessoa=?";
        sSQL2="update tb_clientes set codigo_cliente=?" +
                "where id_pessoa=?";
               
        try {
            
            PreparedStatement pst=cn.prepareStatement(sSQL);
            PreparedStatement pst2=cn.prepareStatement(sSQL2);
            
            pst.setString(1, dts.getNome());
            pst.setString(2, dts.getNome_pai());
            pst.setString(3, dts.getNome_mae());
            pst.setString(4, dts.getTipo_documento());
            pst.setString(5, dts.getNum_documento());
            pst.setString(6, dts.getEndereco());
            pst.setString(7, dts.getTelefone());
            pst.setString(8, dts.getEmail());
            pst.setInt(9, dts.getId_pessoa());
             
            pst2.setString(1, dts.getCodigo_cliente());
            pst2.setInt(2, dts.getId_pessoa());
            
            int n=pst.executeUpdate();
            if (n!=0){
                
                int n2=pst2.executeUpdate();
                if (n2!=0){
                return true;
                }
                
                else{
                return false;
            }
                
            }else{
                return false;
            }
            
        }catch (Exception e){
             JOptionPane.showConfirmDialog(null, e);
             return false;
        }
    }
    
    public boolean deletar (vclientes dts){
        sSQL="delete from tb_clientes where id_pessoa=?";
         sSQL2="delete from tb_pessoas where id_pessoa=?";
         
         try {
            
            PreparedStatement pst=cn.prepareStatement(sSQL);
            PreparedStatement pst2=cn.prepareStatement(sSQL2);
            
          
             pst.setInt(1, dts.getId_pessoa());
             
            
            pst2.setInt(1, dts.getId_pessoa());
            
            int n=pst.executeUpdate();
            if (n!=0){
                
                int n2=pst2.executeUpdate();
                if (n2!=0){
                return true;
                }
                
                else{
                return false;
            }
                
            }else{
                return false;
            }
            
        }catch (Exception e){
             JOptionPane.showConfirmDialog(null, e);
             return false;
        }
    

}

}