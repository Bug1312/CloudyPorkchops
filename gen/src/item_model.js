/*\
|*|========================================================================================================================================= *
|*| GENERATED ITEM MODEL COMMAND PARAMETERS IN ORDER                                                                                         *
|*|  Param Name             | Datatype      | Description                                                           | Default Value          *
|*|-------------------------|---------------|-----------------------------------------------------------------------|----------------------- *
|*|  namespace              | String        | Item's namespace                                                      | null                   *
|*|-------------------------|---------------|-----------------------------------------------------------------------|----------------------- *
|*|========================================================================================================================================= *
\*/

const fs = require("fs-extra");

// Setup command arguments
const args = process.argv
args.shift(); args.shift();

const namespace = args[0];

// Setup folders
fs.mkdir(`${__dirname}/../output/assets/`,(err)=>{});
fs.mkdir(`${__dirname}/../output/assets/${namespace}/`,(err)=>{});
fs.mkdir(`${__dirname}/../output/assets/${namespace}/models/`,(err)=>{});
fs.mkdir(`${__dirname}/../output/assets/${namespace}/models/item/`,(err)=>{});

fs.mkdir(`${__dirname}/../output/assets/`,(err)=>{});
fs.mkdir(`${__dirname}/../output/assets/${namespace}/`,(err)=>{});
fs.mkdir(`${__dirname}/../output/assets/${namespace}/textures/`,(err)=>{});
fs.mkdir(`${__dirname}/../output/assets/${namespace}/textures/item/`,(err)=>{});

// Generate files
fs.readdir(`${__dirname}/../input`, ((err, files) => {
    files = files.filter(n => n != '.dont_ignore');

    files.forEach(fullFileName => {
        let fileName = fullFileName.replace(/\..+/g,'');

        fs.createFileSync(`${__dirname}/../output/assets/${namespace}/models/item/${fileName}.json`);
        fs.writeFileSync(`${__dirname}/../output/assets/${namespace}/models/item/${fileName}.json`, generateJson(namespace, fileName));
        
        fs.moveSync(`${__dirname}/../input/${fullFileName}`, `${__dirname}/../output/assets/${namespace}/textures/item/${fullFileName}`);
    });
}));

// Helper Functions
function generateJson(namespace, id) {
    let output = {
        parent:"item/generated",
        textures:{
           layer0:`${namespace}:item/${id}`
        }
    }
    return JSON.stringify(output);
}