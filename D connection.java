package com.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static Connection con;

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bankdb",
                    "root",
                    "root");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}




index.html


  <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Smart Banking System</title>

<style>

body{
margin:0;
font-family:Arial;
background:#0f172a;
overflow:hidden;
}

.container{
text-align:center;
padding-top:120px;
color:white;
}

h1{
font-size:55px;
animation: glow 2s infinite alternate;
}

@keyframes glow{
from{
text-shadow:0 0 10px cyan;
}
to{
text-shadow:0 0 30px white;
}
}

.btn{
padding:15px 35px;
margin:15px;
border:none;
border-radius:30px;
font-size:18px;
cursor:pointer;
background:cyan;
transition:0.5s;
}

.btn:hover{
transform:scale(1.1);
background:white;
}

.bankimg{
width:220px;
animation: float 3s infinite;
}

@keyframes float{
0%{transform:translateY(0px);}
50%{transform:translateY(-20px);}
100%{transform:translateY(0px);}
}

a{
text-decoration:none;
}

.circle{
position:absolute;
border-radius:50%;
background:rgba(255,255,255,0.1);
animation: move 15s linear infinite;
}

.circle:nth-child(1){
width:200px;
height:200px;
left:10%;
top:20%;
}

.circle:nth-child(2){
width:300px;
height:300px;
right:10%;
bottom:10%;
}

@keyframes move{
0%{transform:rotate(0deg);}
100%{transform:rotate(360deg);}
}

</style>
</head>

<body>

<div class="circle"></div>
<div class="circle"></div>

<div class="container">

<img src="images/bank.png" class="bankimg">

<h1>SMART BANKING SYSTEM</h1>

<a href="addAccount.html">
<button class="btn">Create Account</button>
</a>

<a href="deposit.html">
<button class="btn">Deposit</button>
</a>

<a href="withdraw.html">
<button class="btn">Withdraw</button>
</a>

<a href="transfer.html">
<button class="btn">Transfer</button>
</a>

</div>

</body>
</html>


add account.htm



  <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Account</title>

<style>

body{
font-family:Arial;
background:linear-gradient(to right,#1e3c72,#2a5298);
color:white;
}

form{
width:400px;
margin:auto;
margin-top:40px;
padding:30px;
background:white;
color:black;
border-radius:20px;
box-shadow:0 0 20px black;
}

input,textarea,select{
width:100%;
padding:12px;
margin-top:10px;
border-radius:10px;
border:1px solid gray;
}

button{
width:100%;
padding:15px;
background:#0f172a;
color:white;
border:none;
margin-top:15px;
font-size:18px;
border-radius:10px;
cursor:pointer;
}

button:hover{
background:green;
}

h1{
text-align:center;
padding-top:20px;
}

</style>
</head>

<body>

<h1>Create New Account</h1>

<form action="AddAccountServlet" method="post">

<input type="number" name="accno" placeholder="Account Number">

<input type="text" name="cname" placeholder="Customer Name">

<input type="text" name="mobile" placeholder="Mobile Number">

<input type="email" name="email" placeholder="Email">

<input type="password" name="password" placeholder="Password">

<select name="acc_type">
<option>Savings</option>
<option>Current</option>
</select>

<input type="number" name="balance" placeholder="Initial Balance">

<textarea name="address" placeholder="Address"></textarea>

<button>Create Account</button>

</form>

</body>
</html>


addaccount servlet 
  package com.bank.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.bank.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddAccountServlet")
public class AddAccountServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        int accno =
                Integer.parseInt(request.getParameter("accno"));

        String cname =
                request.getParameter("cname");

        String mobile =
                request.getParameter("mobile");

        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");

        String acc_type =
                request.getParameter("acc_type");

        double balance =
                Double.parseDouble(
                        request.getParameter("balance"));

        String address =
                request.getParameter("address");

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                    "insert into accounts values(?,?,?,?,?,?,?,?)");

            ps.setInt(1, accno);
            ps.setString(2, cname);
            ps.setString(3, mobile);
            ps.setString(4, email);
            ps.setString(5, password);
            ps.setString(6, acc_type);
            ps.setDouble(7, balance);
            ps.setString(8, address);

            int i = ps.executeUpdate();

            if(i > 0){

                out.println("<html><body style='font-family:Arial;background:#0f172a;color:white;text-align:center;padding-top:100px;'>");

                out.println("<h1>Account Created Successfully</h1>");

                out.println("<a href='index.html'>");
                out.println("<button style='padding:15px;background:cyan;border:none;border-radius:10px;'>Home</button>");
                out.println("</a>");

                out.println("</body></html>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





deposit html 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Deposit Money</title>

<style>

body{
font-family:Arial;
background:#111827;
color:white;
}

form{
width:350px;
margin:auto;
margin-top:100px;
padding:30px;
background:white;
color:black;
border-radius:20px;
}

input{
width:100%;
padding:12px;
margin-top:15px;
}

button{
width:100%;
padding:15px;
background:green;
color:white;
border:none;
margin-top:15px;
}

</style>
</head>

<body>

<form action="DepositServlet" method="post">

<h1 align="center">Deposit Money</h1>

<input type="number" name="accno"
placeholder="Account Number">

<input type="number" name="amount"
placeholder="Amount">

<button>Deposit</button>

</form>

</body>
</html>




  deposit servlet 


  package com.bank.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.bank.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet{

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out=response.getWriter();

        int accno=
                Integer.parseInt(
                        request.getParameter("accno"));

        double amount=
                Double.parseDouble(
                        request.getParameter("amount"));

        try {

            Connection con=
                    DBConnection.getConnection();

            PreparedStatement ps=
                    con.prepareStatement(
                    "update accounts set balance=balance+? where accno=?");

            ps.setDouble(1, amount);
            ps.setInt(2, accno);

            int i=ps.executeUpdate();

            if(i>0){

                out.println("<h1>Amount Deposited Successfully</h1>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






  
