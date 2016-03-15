# Lazurite JAVA

Lazurite Java Library


## Installation

'''
git clone git://github.com/LAPIS-Lazurite/LazuriteJava.git
make
make install
make clean (option)l

In default, folder to install library is "/usr/lib/jvm/jdk-8-oracle-arm-vfp-hflt/jre/lib/ext ".
If it is changed, please modify Makefile of LIBPATH.
'''

## Usage
build sample code
'''
cd sample
make
'''
execute sample code

'''
java sample_raw
or
java sample_raw2
or 
java sample_serial

'''
to execute "sample_serial", address from should be set.

document(Japanese only):
  http://www.lapis-semi.com/lazurite-jp/contents/gateway/ruby/LazGem.html

## Development

After checking out the repo, run `bin/setup` to install dependencies. Then, run `rake test` to run the tests. You can also run `bin/console` for an interactive prompt that will allow you to experiment.

To install this gem onto your local machine, run `bundle exec rake install`. To release a new version, update the version number in `version.rb`, and then run `bundle exec rake release`, which will create a git tag for the version, push git commits and tags, and push the `.gem` file to [rubygems.org](https://rubygems.org).

## Contributing

Bug reports and pull requests are welcome on GitHub at https://github.com/LAPIS-Lazurite/LazGem. This project is intended to be a safe, welcoming space for collaboration, and contributors are expected to adhere to the [Contributor Covenant](http://contributor-covenant.org) code of conduct.


## License

The gem is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).

