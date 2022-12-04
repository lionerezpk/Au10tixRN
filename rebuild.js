/**
 * This script will remove current node_modules and ios/Pods directories and re-install both.
 * Notice, script renaming folders names for be able to run installing and deleting directories 
 * in parallel.
 */

const fs = require('fs');
const path = require('path');
const { exec } = require('child_process');

const nodeModulesSource = path.join(__dirname, 'node_modules');
const nodeModulesDest = path.join(__dirname, '_node_modules');
const podsSource = path.join(__dirname, 'ios/Pods');
const podsDest = path.join(__dirname, 'ios/_Pods');
const packageJsonLock = path.join(__dirname, 'package-lock.json');
const podsLock = path.join(__dirname, 'ios/Podfile.lock')

const doWork = async () => {
  removeFolder(packageJsonLock);
  removeFolder(podsLock);
  rename(nodeModulesSource, nodeModulesDest, () => {
    rename(podsSource, podsDest, () => {
      console.log('dir names were change');
      removeOldFolders();
      npmInstall(() => {
        console.log('npm install done');
        podsInstall(() => {
          console.log('pod install done');
        })
      });
    });
  });
}

const rename = async (source, dest, done) => {
  fs.rename(source, dest, done);
}

const removeOldFolders = () => {
  console.log('removing old folders...');
  removeFolder(nodeModulesDest);
  removeFolder(podsDest);
}

const removeFolder = (folder) => {
  fs.rm(folder, { recursive: true, force: true }, () => { });
}

const npmInstall = (done) => {
  console.log("performing npm install...");
  exec('npm install', done);
}

const podsInstall = (done) => {
  console.log('performing pods install...');
  exec("cd ios && pod install", done);
}

doWork();