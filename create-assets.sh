#!/bin/bash

set -e

pinpit create-image-assets --input jpui.svg --output assets

mkdir -p src/main/packaging/linux/
mv assets/icon.png src/main/packaging/linux/

mkdir -p src/main/packaging/windows/
mv assets/icon.ico src/main/packaging/windows/
mv assets/banner.bmp src/main/packaging/windows/
mv assets/dialog.bmp src/main/packaging/windows/

mkdir -p src/main/packaging/macos/
mv assets/icon.icns src/main/packaging/macos/

rm assets/icon.svg
rm assets/banner.svg
rm assets/dialog.svg

rmdir assets
