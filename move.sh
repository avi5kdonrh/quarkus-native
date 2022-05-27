if [ -d /work/application ]; then
  mv /work/application /work/applicationdir
  mv /work/applicationdir/*-runner /work/application
fi