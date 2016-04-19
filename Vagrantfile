# -*- mode: ruby -*-
# vi: set ft=ruby :

# All Vagrant configuration is done below. The "2" in Vagrant.configure
# configures the configuration version (we support older styles for
# backwards compatibility). Please don't change it unless you know what
# you're doing.
Vagrant.configure(2) do |config|
  # The most common configuration options are documented and commented below.
  # For a complete reference, please see the online documentation at
  # https://docs.vagrantup.com.

  # Every Vagrant development environment requires a box. You can search for
  # boxes at https://atlas.hashicorp.com/search.
  config.vm.box = "ubuntu/wily64"



  # Share an additional folder to the guest VM. The first argument is
  # the path on the host to the actual folder. The second argument is
  # the path on the guest to mount the folder. And the optional third
  # argument is a set of non-required options.vaga
  config.vm.synced_folder "provisioning", "/vagrant/provisioning"

  # Provider-specific configuration so you can fine-tune various
  # backing providers for Vagrant. These expose provider-specific options.
  # Example for VirtualBox:
  #
  config.vm.provider "virtualbox" do |vb|

    # Customize the amount of memory on the VM:
    vb.memory = "1024"
    vb.cpus = 2
  end

  AWS_ACCESS_KEY = ENV['DCOS_INTRO_AWS_ACCESS_KEY']
  AWS_SECRET_KEY = ENV['DCOS_INTRO_AWS_SECRET_KEY']
  AWS_REGION = ENV['DCOS_INTRO_AWS_REGION']

  config.vm.provision "ansible" do |ansible|
    ansible.verbose = ""
    ansible.playbook = "provisioning/site.yml"
    ansible.extra_vars = {
                           aws_access_key: AWS_ACCESS_KEY,
                           aws_secret_key: AWS_SECRET_KEY,
                           aws_region: AWS_REGION
                         }
  end
end
