<?php
  $db = mysql_connect("mysql.stud.ntnu.no", "franang_admin", "tranduil123");

  mysql_select_db("franang_insider",$db);
  $result = mysql_query("SELECT * FROM customer",$db);

  printf("Name: %s<br>\n", mysql_result($result,0,"name"));
  printf("Email: %s<br>\n", mysql_result($result,0,"email"));
  printf("Department: %s<br>\n", mysql_result($result,0,"department"));
?>