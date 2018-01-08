<?php
define("DB_HOST","localhost");
define("DB_USER","root");
define("DB_PWD","L3anL4b#");
define("DB_NAME","leanlabworking");

//require_once './rules/forbidden_sql_statements.php';


if (!empty($_POST) || !empty($_GET)) {
    if (!empty($_REQUEST['Username']) && !empty($_REQUEST['Password'])) {
        if (db_connection::db_authenticate($_REQUEST['Username'],$_REQUEST['Password'])) {
            //user authentificated

            //########################################################
            //IMPORTANT: what to do [register here all possible events]
            if (!empty($_REQUEST['qr_code'])) {
                //TODO: Do QR-Code procedures
                db_connection::execSQLStatement_static("SELECT * FROM Station WHERE Stationid=".db_connection::escapeString($_REQUEST['qr_code']).";");
            } else if (!empty($_REQUEST['sql_statement'])) {
                db_connection::execSQLStatement_static($_REQUEST['sql_statement']); //Here no escapeString!
				//always with else if!
            } else if (!empty($_FILES['UploadedFile'])&& !empty($_POST['UploadedFile_type'])){
                $file_path = "./".$_POST['UploadedFile_type']."/".basename($_FILES['UploadedFile'] ['name']);
                if (move_uploaded_file($_FILES['UploadedFile']['tmp_name'], $file_path)){
                    sendHeader("199", "Upload successful", "File uploaded: ".$_POST['UploadedFile_type']);
                }else{
                    sendHeader("199", "Upload unsuccessful", "File not uploaded: ".$_POST['UploadedFile_type']);
                }

            }else {
				sendHeader("109","510 - Not Extended / Empty Response","Further extensions to the request are required for the server to fulfil it.");
			}
            //########################################################
        } else {
            //access forbidden
            sendHeader("103","403 - Forbidden","The request was valid, but the server is refusing action. The user might not have the necessary permissions for a resource, or may need an account of some sort.");
        }
    } else {
        //no valid request
        sendHeader("104","401 - No credentials provided","Authentication is required and has failed or has not yet been provided. 401 semantically means unauthenticated, i.e. the user does not have the necessary credentials.");
    }
} else {
    //no request
    sendHeader("105", "412 - Precondition Failed","The server does not meet one of the preconditions that the requester put on the request.");
}

function sendHeader($code="0", $reason="Unknown error", $description="no description provided", $data="{}") {
    //IMPORTANT: NO OUTPUT ALLOWED BEFORE CALLING THIS FUNCTION
    //header(trim("HTTP/1.1 $code $reason")); //IMPORTANT: do not use error codes ABOVE 399 (must be < 400) UNLESS you want the Android APP to stop!
	
	echo "{\"HEADER_RESP\":{\"Code\":\"$code\",\"Reason\":\"$reason\",\"Description\":\"The server responded with an error code of $code and provided following message for you: $description\"},\"DATA\":{\"ResultObj\":$data}}";

    //echo "<h1>$code: $reason</h1> <p>The server responded with an error code of $code and provided following message for you: <br /><i>'$description'</i></p>";
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
            sendHeader("106","504 - Gateway Timeout","The server was acting as a gateway or proxy and did not receive a timely response from the upstream server. Additional information: ".mysqli_connect_error());
        }

        mysqli_set_charset($con,"utf8");
        return $this->setCon($con); //setter is simultaneously a getter
    }

    public static function db_authenticate($user,$password) {
        $isValid = false;

        $user_obj = db_connection::db_getUserObj($user);

        if (!empty($user_obj[0]['Password'])) { //first row = 0
            if (password_verify($password, $user_obj[0]['Password'])) {
                $isValid = true;
            } else {
                sendHeader("108","403_2 - Forbidden","Password is wrong.");
            }
        } else {
            sendHeader("107","501 - Not implemented","The server either does not recognize the request method, or it lacks the ability to fulfill the request. Usually this implies future availability (e.g., a new feature of a web-service API). It is also possible that the current user has no password!");
        }
        return $isValid;
    }

    public static function escapeString($string) {
        $con = new db_connection();
        $con = $con->openConnection();
        return $con->real_escape_string($string);
    }

    private static function db_getUserObj($user) {
        return db_connection::execSQLStatement_PHP_static("SELECT * FROM User WHERE Username='".self::escapeString($user)."';");
    }

    // ##################### BASIC FUNCTIONS END #############################

    private static function execSQLStatement_PHP_static($query) {
        $send_sql = new db_connection();
        $send_sql->setQuery($query);

        $con = $send_sql->openConnection();
        $sth = $con->query($send_sql->getQuery()) or trigger_error(mysqli_error($con)." ".$query);

        $result = array();$i=0;

        if (!is_object($sth)) {
            sendHeader("108","404_SQL - Sent SQL Statement has no result.","If sql statement was not a query (INSERT; UPDATE,...) then this response is normal. It is also possible that your query delivered no results or the requested table does not exist in your database.");
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
        if (is_object($sth)){
        if ($sth->num_rows > 0) {
            while ($row = $sth->fetch_assoc()) {
                $result[$i++] = $row;
            }
        }
        }
        $res = json_encode($result, JSON_FORCE_OBJECT); //so immer {} umschließend statt []
        //echo $res; //das wird in JAVA zurückgegeben
		sendHeader("200", "200 - OK","Request successful!",$res); //einheitliches JSON ausgeben
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
