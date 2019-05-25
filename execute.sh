
params=""
params="$params -Xms24m -Xmx120m -XX:MaxMetaspaceSize=70m "
params="$params -XX:MaxHeapFreeRatio=2 -XX:MinHeapFreeRatio=1 "
#params="$params -XX:+UnlockCommercialFeatures "
#params="$params -XX:+FlightRecorder "
# -XX:StartFlightRecording=duration=10s,filename=myrecording.jfr
# Para ativar o debugger
params="$params -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=1044 "


java $params -jar ./target/spring_data_ebean_1.jar
