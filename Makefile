# --------------------------------------------------------------------
# Author: Menuka Warushavithana
# --------------------------------------------------------------------

.PHONY: build
build:
	sbt clean assembly;

run:
	spark-submit --class org.sustain.Neo4JConnect  \
				 --supervise target/scala-2.12/sustain-neo4j-spark-assembly-0.1.jar;

clean:
	sbt clean
