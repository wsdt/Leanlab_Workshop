<?php
define("DB_HOST","localhost");
define("DB_USER","root");
define("DB_PWD","NjGrdE9Rjkj32nhvVGf89aQgOIJ5H65O");
define("DB_NAME","LeanLab");


if (!empty($_POST) || !empty($_GET)) {
    if (!empty($_REQUEST['user']) && !empty($_REQUEST['password'])) {
        if (db_connection::db_authenticate($_REQUEST['user'],$_REQUEST['password'])) {
            //user authentificated

            //########################################################
            //IMPORTANT: what to do [register here all possible events]
            if (!empty($_REQUEST['sql_statement'])) {
                db_connection::execSQLStatement_static($_REQUEST['sql_statement']);
            } //always with else if!

            //########################################################
        } else {
            //access forbidden
            sendHeader("403","Forbidden","The request was valid, but the server is refusing action. The user might not have the necessary permissions for a resource, or may need an account of some sort.");
        }
    } else {
        //no valid request
        sendHeader("401","No credentials provided","Authentication is required and has failed or has not yet been provided. 401 semantically means unauthenticated, i.e. the user does not have the necessary credentials.");
    }
} else {
    //no request
    sendHeader("412", "Precondition Failed","The server does not meet one of the preconditions that the requester put on the request.");
}

function sendHeader($code="0", $reason="Unknown error", $description="{no description provided}") {
    //IMPORTANT: NO OUTPUT ALLOWED BEFORE CALLING THIS FUNCTION
    header(trim("HTTP/1.1 $code $reason"));
	
	echo "<script type='text/javascript'>";
	foreach (getallheaders() as $name => $value) {
    echo "console.log('$name: $value\\n');";
	}
	echo "</script>";

    echo "<h1>$code: $reason</h1> <p>The server responded with an error code of $code and provided following message for you: <br /><i>'$description'</i></p>";
    die();
}


class db_connection {
    private $query;
    private $con;

    public function __construct() {
        //do not add here standard setter
    }

    // #################### BASIC FUNCTIONS ##################################
    private function openConnection() {
        $con = @new mysqli(DB_HOST,DB_USER,DB_PWD,DB_NAME);

        if (mysqli_connect_errno()) {
            sendHeader("504","Gateway Timeout","The server was acting as a gateway or proxy and did not receive a timely response from the upstream server.<br /><br />Additional information: ".mysqli_connect_error());
        }

        mysqli_set_charset($con,"utf8");
        return $this->setCon($con); //setter is simultaneously a getter
    }

    public function escapeString($string) {
        return $this->real_escape_string($string);
    }

    public static function db_authenticate($user,$password) {
        $isValid = false;

        $user_obj = db_connection::db_getUserObj($user);

        if (!empty($user_obj['password'])) {
            if (password_verify($password, $user_obj['password'])) {
                $isValid = true;
            }
        } else {
            sendHeader("501","Not implemented","The server either does not recognize the request method, or it lacks the ability to fulfill the request. Usually this implies future availability (e.g., a new feature of a web-service API).");
        }
        return $isValid;
    }

    private static function db_getUserObj($user) {
        return db_connection::execSQLStatement_PHP_static("SELECT * FROM Users WHERE username='".$user."';");
    }

    // ##################### BASIC FUNCTIONS END #############################

    private function execSQLStatement_PHP_static($query) {
        $send_sql = new db_connection();
        $send_sql->setQuery($query);

        $con = $send_sql->openConnection();
        $sth = $con->query($send_sql->getQuery());

        $result = array();$i=0;

        if (!is_object($sth)) {
            sendHeader("404_SQL","Sent SQL Statement has no result.","If sql statement was not a query (INSERT; UPDATE,...) then this response is normal. It is also possible that your query delivered no results or the requested table does not exist in your database.");
        }

        if ($sth->num_rows > 0) {
            while ($row = $sth->fetch_array(MYSQLI_BOTH)) {
                $result[$i++] = $row;
            }
        }
        $con->close();
        unset($send_sql);

        return $result;
    }

    public static function execSQLStatement_static($query) {
        //makes code shorter
        $send_sql = new db_connection();
        $send_sql->setQuery($query);
        $send_sql->execSQLStatement();
        unset($send_sql);
    }

    private function execSQLStatement() {
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
