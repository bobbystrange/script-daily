# devops script

### add to PATH

```bash
source=`pwd`
mkdir -p $HOME/.local/bin
target=$HOME/.local/bin

# gradle
ln -s $source/other/shell/gradle/add-gradle-submodule.sh $target/add-gradle-submodule
# ln -s $source/other/shell/gradle/ls-to-settings.sh $target/ls-to-settings

# docker
ln -s $source/other/shell/docker/build-docker.sh $target/build-docker

```
