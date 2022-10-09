/*\
|*|========================================================================================================================================= *
|*| LOOT TABLE COMMAND PARAMETERS IN ORDER                                                                                                   *
|*|  Param Name             | Datatype      | Description                                                           | Default Value          *
|*|-------------------------|---------------|-----------------------------------------------------------------------|----------------------- *
|*|  namespace              | String        | Loot table's namespace                                                | null                   *
|*|-------------------------|---------------|-----------------------------------------------------------------------|----------------------- *
|*|========================================================================================================================================= *
\*/

const fs = require("fs-extra");

// Setup command arguments
const args = process.argv
args.shift();
args.shift();

const namespace = args[0];

// Setup folders
fs.mkdir(`${__dirname}/../output/data/`, (err) => {});
fs.mkdir(`${__dirname}/../output/data/${namespace}/`, (err) => {});
fs.mkdir(`${__dirname}/../output/data/${namespace}/loot_tables/`, (err) => {});
fs.mkdir(`${__dirname}/../output/data/${namespace}/loot_tables/blocks/`, (err) => {});

fs.mkdir(`${__dirname}/../output/assets/`, (err) => {});
fs.mkdir(`${__dirname}/../output/assets/${namespace}/`, (err) => {});
fs.mkdir(`${__dirname}/../output/assets/${namespace}/blockstates/`, (err) => {});

// Generate files
fs.readdir(`${__dirname}/../input`, ((err, files) => {
    files = files.filter(n => n != '.dont_ignore');

    files.forEach(fullFileName => {
        let fileName = fullFileName.replace(/\..+/g, '');

        fs.createFileSync(`${__dirname}/../output/data/${namespace}/loot_tables/blocks/${fileName}.json`);
        fs.writeFileSync(`${__dirname}/../output/data/${namespace}/loot_tables/blocks/${fileName}.json`, generateJson(namespace, fileName));

        fs.moveSync(`${__dirname}/../input/${fullFileName}`, `${__dirname}/../output/assets/${namespace}/blockstates/${fullFileName}`);
    });

}));

// Helper Functions
function generateJson(namespace, id) {
    let output = {
        type: "minecraft:block",
        pools: [{
            rolls: 1,
            entries: [{
                type: "minecraft:item",
                name: `${namespace}:${id}`
            }],
            conditions: [{
                condition: "minecraft:survives_explosion"
            }]
        }]
    }
    return JSON.stringify(output);
}