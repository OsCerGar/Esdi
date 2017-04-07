
<?php

include 'config.inc.php';

// Check whether username or password is set from android	
if (isset($_POST['username'])) {
    // Innitialize Variable
    $result = '';
    $username = $_POST['username'];
    $dia = $_POST['dia'];
    $aula = $_POST['aula'];

    // Query database for row exist or not
    $sql = 'select aula from clase where horainicio <= (Select codigohora 
								from HORARIO 
								where hora = :username AND dia = :dia ) 
					AND horafinal >= (Select codigohora 
						from HORARIO
							where hora = :username AND dia = :dia )
                                        AND aula LIKE :aula ';
    $stmt = $conn->prepare($sql);
    //Parameter name, Parameter value, Parameter type
    $stmt->bindParam(':username', $username, PDO::PARAM_STR);
    $stmt->bindParam(':dia', $dia, PDO::PARAM_STR);
    $stmt->bindParam(':aula', $aula, PDO::PARAM_STR);
    $stmt->execute();



    if ($stmt->rowCount()) {
        $obj = $stmt->fetchObject();
        $result = $obj->aula;

        // $result = "admin";
    } elseif (!$stmt->rowCount()) {
        $result = "nada";
    }
    $result = "h2";

    // send result back to android
    echo $result;
}
?>