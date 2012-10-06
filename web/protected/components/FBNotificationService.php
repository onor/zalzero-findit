<?php
class FBNotificationService {
	private $appId;
	private $appSecret;
	private $appAccessToken;

	function __construct($appId, $appSecret) {
		$this->appId = $appId;
		$this->appSecret = $appSecret;

		$tokenUrl = "https://graph.facebook.com/oauth/access_token?" .
			"client_id=" . $appId .
			"&client_secret=" . $appSecret .
			"&grant_type=client_credentials";

		$this->appAccessToken = file_get_contents($tokenUrl);
	}

	function sendNotification($id, $notificationMessage, $gameId) {
		$apprequestUrl ="https://graph.facebook.com/" . $id . '/notifications?' . $this->appAccessToken . '&template=' . urlencode($notificationMessage) . '&href=?gameinst_id=' . $gameId . "&method=post";
		$result = @file_get_contents($apprequestUrl);
		$resultObj = json_decode($result);
		return isset($resultObj->success);
	}

	/*
	 * This function is responsible to create the comma seperated list of fb ids for messages in the following format
	 * Only one other friend: you and {FBID1}
	 * Two or More other friend: you, {FBID1},... and {FBID2}
	 */
	function getCommaSeperateListOfFBFriends($fbFriends, $currentFBUser) {
		$message = "you";
		if(count($fbFriends) > 1) {
			foreach($fbFriends as $data) {
				if($data['uid'] != $currentFBUser) {
					$otherNames[] = "{" . $data['uid'] . "}" ;
				}
			}
			$data = implode(", ",$otherNames);
			$replace = ' and ';
			$search = ',';
			$messageToAppend = str_lreplace($search, $replace, $data);
			if(count($otherNames) == 1) {
				$message .= " and ". $messageToAppend;
			} else {
				$message .= ", ". $messageToAppend;	
			}
		}
		return $message;
	}
}