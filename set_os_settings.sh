#!/usr/bin/env bash
groupadd docker
usermod -aG docker $USER

sysctl -w vm.max_map_count=262144