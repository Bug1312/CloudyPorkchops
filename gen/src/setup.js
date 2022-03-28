const fs = require("fs");

fs.mkdir(`${__dirname}/../output`,(err)=>{});

/* Code for other files */

/*\
|*|========================================================================================================================================= *
|*| <COMMAND> COMMAND PARAMETERS IN ORDER                                                                                                             *
|*|  Param Name             | Datatype      | Description                                                           | Default Value          *
|*|-------------------------|---------------|-----------------------------------------------------------------------|----------------------- *
|*|                         |               |                                                                       | null                   *
|*|-------------------------|---------------|-----------------------------------------------------------------------|----------------------- *
|*|========================================================================================================================================= *
\*/

// Setup command arguments
let args = process.argv
args.shift(); args.shift();

if (args[0] == "-h" || args[0] == "--help" || !args[0]) {} else {}