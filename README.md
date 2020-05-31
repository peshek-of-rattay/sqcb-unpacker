# SQCB Unpacker

SQCB Unpacker is a command line tool that unpacks SQCB files.

The SQCB format is used by [Kingdom Come: Deliverance](https://www.kingdomcomerpg.com/) for storage of music.



## Build

In order to build SQCB Unpacker you need Java SE Development Kit (JDK) 8 or higher
and [Apache Maven](https://maven.apache.org/).

You can build SQCB Unpacker using Apache Maven via the command:

`mvn package`

The command creates the ``sqcb-unpacker.jar`` file in the ``target`` directory.



## Run

From the command line, run the command:

`java -jar sqcb-unpacker.jar FILE`

or

`java -jar sqcb-unpacker.jar DIRECTORY`


SQCB Unpacker unpacks the specified SQCB ``FILE`` or all SQCB files contained in
the specified ``DIRECTORY`` and its subdirectories.


**Example**

`java -jar sqcb-unpacker.jar "C:\Directory of SQCB files"`



## References

[Sequence Music Engine in Kingdom Come: Deliverance](https://wiki.nexusmods.com/index.php/Sequence_Music_Engine_in_KCD)



## License

This software is available under the [MIT](https://opensource.org/licenses/MIT) license.
