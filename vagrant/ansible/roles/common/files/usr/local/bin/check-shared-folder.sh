#!/usr/bin/env bash
check_shared_folder() {
  local prog=$1;
  for ((i = 1; i <= 6; i++))
  do
      [ -f /aeromock/aeromock ] && return 0
      sleep 5
  done

  /usr/bin/logger -t check_shared_folder "Can not start $prog, wait for mounting shared folder of vagrant."
  return 1
}
