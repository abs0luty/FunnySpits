<p align="center"><img src="./animation.gif" /></p>

# How to spit?
Just use `/funnyspits spit` command!

# Configuration
The configuration file is only one and called `config.yml`.

> To reload config file values use `/funnyspits reload` command.

## `spit_command_permission`:
If you want only players who have the specific permission to be able to "spit", change the parameter's value to that specific permission. 
If you want everybody to be able to "spit", then set its value to `''`.

## `spit_command_not_enough_permissions_message`: 
Message that shows up if player doesn't have specified earlier permission, but tries to run the spit command.

## `spit_with_sound`:
Set to `true` if you want sound of llama spit to be played near the location of where the player has spat at else set to `false`.

## `spit_intensity`:
Intensity value of "spit" (number), value is required to be in the interval of numbers from 1 to 10.

## `reload_command_permission`:
Permission for that player to be able to run plugin config reload command.

## `reload_command_not_enough_permissions_message`:
Message that shows up if player doesn't have specified earlier permission, but tries to run the reload command.

## `reload_command_success_message`:
Message that shows up if plugin was reloaded successfully.
