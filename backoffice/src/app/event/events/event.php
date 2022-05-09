<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: PUT, GET, POST");
header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept");
 
$calendarEvents = array('title' => 'Event name', 'start' => '2021-05-10');
 
echo json_encode($calendarEvents);