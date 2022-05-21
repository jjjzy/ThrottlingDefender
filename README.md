cd nginx

export PATH=/usr/local/openresty/nginx/sbin:$PATH

start:
nginx -p $PWD/

stop:
sudo pkill -f nginx & wait $!
