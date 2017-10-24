<?php
define("DB_HOST","localhost");
define("DB_USER","root");
define("DB_PWD","NjGrdE9Rjkj32nhvVGf89aQgOIJ5H65O");
define("DB_NAME","LeanLab");


if (!empty($_POST)) {
    $post_request = new db_connection($_POST['sql_statement']);
    $post_request->execSQLStatement();
}



class db_connection {
    private $query;
    private $con;

    public function __construct($query) {
        $this->setQuery($query);
    }

    // #################### BASIC FUNCTIONS ##################################
    private function openConnection() {
        $con = new mysqli(DB_HOST,DB_USER,DB_PWD,DB_NAME) or die (mysqli_error($con));
        mysqli_set_charset($con,"utf8");
        return $this->setCon($con); //setter is simultaneously a getter
    }

    public function escapeString($string) {
        return $this->real_escape_string($string);
    }

    // ##################### BASIC FUNCTIONS END #############################

    public function execSQLStatement() {
        $con = $this->openConnection();

        $sth = $con->query($this->getQuery());

        $result = array();$i=0;
        if ($sth->num_rows > 0) {
            while ($row = $sth->fetch_assoc()) {
                $result[$i++] = $row;
            }
        }

        $res = json_encode($result);
        echo $res; //das wird in JAVA zurückgegeben
        mysqli_free_result($sth);

        $con->close();
        return $result;
    }

    public function setQuery($query) {
        $this->query = $query; //Do not escape query!! (it should be a sql statement)
    }
    public function getQuery() {
        return $this->query; //Do not escape query!! (it should be a sql statement)
    }
    public function setCon($con) {
        $this->con = $con; //Do not escape con!! (it should be a mysqli object)
        return $con;
    } //IMPORTANT: No GETTER for CON because there should be only one variable


}



/*
class db_connection
{
    //Bis jetzt können auch anonyme User Anfragen senden!! (Security risk)
    //function __construct() {
    public function makeRequest() {
        $con = new mysqli(DB_HOST,DB_USER,DB_PWD,DB_NAME) or die (mysqli_error($con));
        //$con = mysqli_connect(DB_HOST,DB_USER,DB_PWD,DB_NAME) or die (mysqli_error($con));
        mysqli_set_charset($con,"utf8");
        $query = $_POST['sql_statement'];
        //$query = file_get_contents("php://input");

        //self::isValidRequest($query); //Script gets exited if query is not valid/allowed!
        $sth = $con->query($query);
        //$sth = mysqli_query($con,$query);

        /*if (mysqli_errno($con)) {
            header("HTTP/1.1 500 Internal Server Error");
            echo $query.'\n';
            echo mysqli_error($con);
        } else {
            $rows = array();
            while ($r = mysqli_fetch_assoc($sth)) {
                $rows[] = $r;
            }
            $res = json_encode($rows);
            echo $res;
            mysqli_free_result($sth);
        }

        $result = array();$i=0;
        if ($sth->num_rows > 0) {
            while ($row = $sth->fetch_assoc()) {
                $result[$i++] = $row;
            }
        }

        //mysqli_close($con);
        $con->close();
        return $result;
    }

}
*/