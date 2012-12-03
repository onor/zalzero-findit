/*<script type="javascript">
//==============================================================================
// VARIABLES
//==============================================================================
// Application variables
var net;
var orbiter;
var msgManager;
var UPC;
var clients;
 
// Command-line arguments
var host;
var port;
var numClients;
var sendMessageFrequency;
var debugMode = false;
 
//==============================================================================
// BOOT FUNCTIONS
//==============================================================================
function main () {
  // Quit if command line args don't validate
  var args = process.argv.slice(2);
  if (args.length < 4) {
    usage();
    return;
  }
 
  out('');
  out('==============================');
  out('* OrbiterMicroNode Load Test *');
  out('==============================');
  out('Booting... Use [q] to exit.');
 
  // Parse command-line args
  host = args[0];
  port = parseInt(args[1]);
  numClients = parseInt(args[2]);;
  sendMessageFrequency = parseInt(args[3]);
  debugMode = args[4] == "debug";
 
  // Can't continue with invalid input
  if (isNaN(port) || port < 1) {
    out("Invalid port specified: " + args[1]);
    process.exit();
  }
  if (isNaN(numClients) || numClients < 1) {
    out("Invalid numClients specified: " + args[2]);
    process.exit();
  }
  if (isNaN(sendMessageFrequency) || sendMessageFrequency < 1) {
    out("Invalid messageFrequency specified: " + args[3]);
    process.exit();
  }
 
  // Load OrbiterMicroNode module
  net = require('OrbiterMicroNode_2.0.0.136_Release').net;
 
  // Listen for command-line input
  var stdin = process.openStdin();
  stdin.on('data', onStdInData);
 
  // Assign initial variable values
  UPC = net.user1.orbiter.UPC;
  clients = [];
 
  // Start the test
  run();
}
 
function usage() {
  out('Usage:');
  out('node OrbiterMicroNodeLoadTest.js [host] [port] [numClients] [messageFrequency] [debug]');
  out('  -debug (optional) enables debug logging')
  out('E.g. Ten clients; each client sends a message every 500ms (no debugging):');
  out('  node OrbiterMicroNodeLoadTest.js example.com 80 10 500');
  out('E.g. Two clients; each client sends a message every 1000ms (with debugging):');
  out('  node OrbiterMicroNodeLoadTest.js example.com 80 2 1000 debug');
  process.exit();
}
 
function run () {
  // Output the test parameters to the console
  out('Connecting ' + numClients + ' client(s) to ' + host + ':' + port
      + ' with messageFrequency: ' + sendMessageFrequency);
  // Connect the first client
  addClient();
}
 
//==============================================================================
// CLIENT CREATION
//==============================================================================
function addClient () {
  // Create the client
  orbiter = new net.user1.orbiter.Orbiter();
  orbiter.addEventListener(net.user1.orbiter.OrbiterEvent.READY, readyListener, this);
  orbiter.addEventListener(net.user1.orbiter.OrbiterEvent.CLOSE, closeListener, this);
 
  // Send a message to self every sendMessageFrequency milliseconds
  msgManager = orbiter.getMessageManager();
  var intervalID = setInterval (function () {
    if (orbiter.isReady()) {
      msgManager.sendUPC(UPC.SEND_MESSAGE_TO_CLIENTS, "TEST", orbiter.getClientID());
    } else {
      clearInterval(intervalID);
    }
  }, sendMessageFrequency);
 
  // Register for log events if requested
  if (debugMode) {
    orbiter.getLog().setLevel("debug");
    orbiter.getLog().addEventListener("UPDATE", logUpdateListener);
  }  
 
  // Add the client to the list of clients
  clients.push(orbiter);
 
  // Connect to Union Server
  orbiter.connect(host, port);
}
 
//==============================================================================
// ORBITER EVENT LISTENERS
//==============================================================================
// Triggered when a client's connection is ready
function readyListener (e) {
  // If the requested number of clients has not yet connected, connect another client
  if (clients.length < numClients) {
    if (clients.length % Math.floor(numClients/10) == 0) {
      out('    ' + clients.length + ' clients connected.');
    }
    addClient();
  } else {
    out('Done. Successfully connected ' + clients.length + ' client(s).');
    out('Test in progress...');
  }
}
 
// Triggered when a client connection is closed
function closeListener (e) {
  out("Orbiter connection closed.");
}
 
//==============================================================================
// LOG UPDATE LISTENER
//==============================================================================
function logUpdateListener (e) {
  out(e.getLevel() + ": " + e.getMessage());
}
 
//==============================================================================
// STD IN/OUT
//==============================================================================
function out (msg) {
  console.log(msg);
}
 
function onStdInData (data) {
  var data = data.toString().trim();
  var cmd = data.split(':')[0];
  var data = data.split(':')[1];
  switch (cmd) {
    case 'exit':
    case 'quit':
    case 'q':
      process.exit();
      break;
  }
}
 
//==============================================================================
// BOOT THE APP
//==============================================================================
main();
<>*/