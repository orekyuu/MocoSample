# Moco Sample
[Moco Framework](https://github.com/orekyuu/moco-framework)のサンプルリポジトリです。

# 実行方法
実行にはMySQLが必要です。以下のどちらかの方法で準備して下さい。

## Vagrantを使う方法
`vagrant up`を叩いて起動後にMain.javaを実行

## 手持ちのMySQL Serverを使う場合
1. `scripts/schema.sql`を実行
1. Main#setup内の接続情報を書き換える
