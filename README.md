# GymPhysique
-------------
Android app intended for measure body composition measurements with use of BLE, App is multi-module and created with most robust technologies and patterns. The application may have flaws, as it was based on a reverse engineering mechanism and public devices. A App functionalities:
* Create account with firstname, surname, height, age, image
* With use of BLE, scan advertisements whose has bodyComposition service, connect to device, await for incoming measurements.
* Show measurements history on array
* Update account settings with settings feature.
* Daily reminders when user forgot about measurement.

## Screenshots & Gifs:
### Account setup
<img src="./gifs/account_setup.gif" width="70%" height="70%">

### Measure screen
<img src="./gifs/measure_screen.gif" width="70%" height="70%">

### Charts screen
<img src="./gifs/charts_screen.gif" width="70%" height="70%">

### Settings screen
<img src="./gifs/settings_screen.gif" width="70%" height="70%">

## Installation
1. Download .zip
   or clone the source locally
```sh
$ git clone https://github.com/Daves9809/GymPhysique
```

## Special Thanks
@wiecosystem: https://github.com/wiecosystem/Bluetooth - xiaomi mi scale 2 reverse engineering