/* jshint ignore:start */

/* jshint ignore:end */

define('xtalus/app', ['exports', 'ember', 'ember/resolver', 'ember/load-initializers', 'xtalus/config/environment'], function (exports, Ember, Resolver, loadInitializers, config) {

    'use strict';

    var App;

    Ember['default'].MODEL_FACTORY_INJECTIONS = true;

    App = Ember['default'].Application.extend({
        modulePrefix: config['default'].modulePrefix,
        podModulePrefix: config['default'].podModulePrefix,
        Resolver: Resolver['default'] });

    $ISIS.settings = {
        baseurl: 'http://xtalus.apps.gedge.nl/simple/restful/services/info.matchingservice.dom.Api.api/',
        method: 'GET' };

    loadInitializers['default'](App, config['default'].modulePrefix);

    exports['default'] = App;

});
define('xtalus/controllers/login', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var LoginController = Ember['default'].Controller.extend({

        actions: {
            login: function login() {
                $ISIS.auth.login(this.get("username"), this.get("password")).then((function (data) {
                    console.log(data);
                    if (data.message) {
                        this.set("message", data.message);
                        return;
                    }
                    if (data.success) {
                        this.get("target.router").refresh();
                    }
                }).bind(this));

                return false;
            } } });
    exports['default'] = LoginController;

});
define('xtalus/controllers/me', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var MeController = Ember['default'].Controller.extend({

        actions: {}

    });

    exports['default'] = MeController;

});
define('xtalus/controllers/me/connections', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var MeConnectionController = Ember['default'].Controller.extend({

        actions: {
            showConnectionDetails: function showConnectionDetails(connection) {
                this.set('selectedPerson', connection);
                $('section#page.network').addClass('show-details');
                return false;
            },

            hideConnectionDetails: function hideConnectionDetails() {
                $('section#page.network').removeClass('show-details');
                return false;
            } } });

    exports['default'] = MeConnectionController;

});
define('xtalus/controllers/me/projects', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var MeProjectsController = Ember['default'].Controller.extend({

        getDemandByID: function getDemandByID(uniqueID) {
            return $ISIS.init().then(function (isis) {
                return isis.findDemandByUniqueId.invoke({
                    uUID: uniqueID });
            });
        },

        actions: {

            showDetails: function showDetails(itemID) {
                this.set('selectedDemand', null);
                this.getDemandByID(itemID).then((function (demand) {
                    this.set('selectedDemand', demand);
                }).bind(this));

                Ember['default'].$('body').addClass('aside-right-visible');
                return false;
            },

            hideDetails: function hideDetails() {
                Ember['default'].$('body').removeClass('aside-right-visible');
                return false;
            },

            showPopup: function showPopup(name) {
                Ember['default'].$('section#page.projects').toggleClass('popup-' + name);
                return false;
            },

            closePopup: function closePopup(name) {
                Ember['default'].$('section#page.projects').removeClass('popup-' + name);
                return false;
            },

            delProject: function delProject() {
                var self = this;
                var demand = this.get('selectedDemand');
                var confirmed = confirm('Weet je het zeker?');

                if (confirmed) {
                    demand.deleteDemand.invoke({ confirmDelete: confirmed }).then(function () {
                        self.send('refreshDemands');
                        self.send('hideDetails');
                    });
                }
            },

            createProject: function createProject() {
                var self = this;
                var title = this.get('title');
                var summary = this.get('summary');
                var story = this.get('story');

                this.get('activePerson').createPersonsDemand.invoke({
                    demandDescription: title,
                    demandSummary: summary,
                    demandStory: story
                }).then(function () {
                    self.send('refreshDemands');
                    self.send('closePopup', 'new-project');
                });
            }
        }
    });

    exports['default'] = MeProjectsController;

});
define('xtalus/controllers/profile', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProfileController = Ember['default'].Controller.extend({

        actions: {}

    });

    exports['default'] = ProfileController;

});
define('xtalus/controllers/profile/connections', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProfileNetworkController = Ember['default'].Controller.extend({

        actions: {
            showConnectionDetails: function showConnectionDetails(connection) {
                this.set('selectedPerson', connection);
                $('section#page.network').addClass('show-details');
                return false;
            },

            hideConnectionDetails: function hideConnectionDetails() {
                $('section#page.network').removeClass('show-details');
                return false;
            },

            getUserProfile: function getUserProfile(userID) {
                this.transitionToRoute('profile', userID);
            } } });

    exports['default'] = ProfileNetworkController;

});
define('xtalus/controllers/profile/index', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProfileIndexController = Ember['default'].Controller.extend({

        setPersonData: function setPersonData(person) {
            var self = this;

            var picture = person.picture.split(':');
            this.setProperties({
                birthdate: person.dateOfBirth,
                roles: person.roles,
                profilePicture: 'data:image/png;base64,' + picture[2] });

            person.collectSupplies.extract().then(function (result) {
                return result[0].collectSupplyProfiles.extract().then(function (result) {
                    return result[0].collectProfileElements.extract();
                });
            }).then(function (result) {
                $.each(result, function (i, supply) {

                    if (supply.profileElementDescription === 'PASSION_ELEMENT') {
                        self.set('passion', supply.textValue);
                    }

                    if (supply.profileElementDescription === 'QUALITY_TAGS_ELEMENT') {
                        supply.collectTagHolders.extract().then(function (result) {
                            var qualities = [];
                            $.each(result, function (i, tagholder) {
                                qualities.push(tagholder.tag.title);
                            });
                            self.set('qualities', qualities);
                        });
                    }
                });
            });
        }
    });

    exports['default'] = ProfileIndexController;

});
define('xtalus/controllers/profile/projects', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProjectenController = Ember['default'].Controller.extend({

        getDemandByID: function getDemandByID(uniqueID) {
            return $ISIS.init().then(function (isis) {
                return isis.findDemandByUniqueId.invoke({
                    uUID: uniqueID });
            });
        },

        actions: {

            showDetails: function showDetails(itemID) {
                this.set('selectedDemand', null);
                this.getDemandByID(itemID).then((function (demand) {
                    this.set('selectedDemand', demand);
                }).bind(this));

                Ember['default'].$('body').addClass('aside-right-visible');
                return false;
            },

            hideDetails: function hideDetails() {
                Ember['default'].$('body').removeClass('aside-right-visible');
                return false;
            },

            showPopup: function showPopup(name) {
                Ember['default'].$('section#page.projects').toggleClass('popup-' + name);
                return false;
            },

            closePopup: function closePopup(name) {
                Ember['default'].$('section#page.projects').removeClass('popup-' + name);
                return false;
            },

            delProject: function delProject() {
                var self = this;
                var demand = this.get('selectedDemand');
                var confirmed = confirm('Weet je het zeker?');

                if (confirmed) {
                    demand.deleteDemand.invoke({ confirmDelete: confirmed }).then(function () {
                        self.send('refreshDemands');
                        self.send('hideDetails');
                    });
                }
            },

            createProject: function createProject() {
                var self = this;
                var title = this.get('title');
                var summary = this.get('summary');
                var story = this.get('story');

                this.get('activePerson').createPersonsDemand.invoke({
                    demandDescription: title,
                    demandSummary: summary,
                    demandStory: story
                }).then(function () {
                    self.send('refreshDemands');
                    self.send('closePopup', 'new-project');
                });
            }
        }
    });

    exports['default'] = ProjectenController;

});
define('xtalus/controllers/project', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProjectController = Ember['default'].Controller.extend({

        actions: {}

    });

    exports['default'] = ProjectController;

});
define('xtalus/controllers/project/index', ['exports', 'ember'], function (exports, Ember) {

	'use strict';

	var ProjectIndexController = Ember['default'].Controller.extend({});

	exports['default'] = ProjectIndexController;

});
define('xtalus/controllers/project/matching', ['exports', 'ember'], function (exports, Ember) {

	'use strict';

	var ProjectMatchingController = Ember['default'].Controller.extend({});

	exports['default'] = ProjectMatchingController;

});
define('xtalus/initializers/app-version', ['exports', 'xtalus/config/environment', 'ember'], function (exports, config, Ember) {

  'use strict';

  var classify = Ember['default'].String.classify;
  var registered = false;

  exports['default'] = {
    name: 'App Version',
    initialize: function initialize(container, application) {
      if (!registered) {
        var appName = classify(application.toString());
        Ember['default'].libraries.register(appName, config['default'].APP.version);
        registered = true;
      }
    }
  };

});
define('xtalus/initializers/export-application-global', ['exports', 'ember', 'xtalus/config/environment'], function (exports, Ember, config) {

  'use strict';

  exports.initialize = initialize;

  function initialize(container, application) {
    var classifiedName = Ember['default'].String.classify(config['default'].modulePrefix);

    if (config['default'].exportApplicationGlobal && !window[classifiedName]) {
      window[classifiedName] = application;
    }
  }

  ;

  exports['default'] = {
    name: 'export-application-global',

    initialize: initialize
  };

});
define('xtalus/router', ['exports', 'ember', 'xtalus/config/environment'], function (exports, Ember, config) {

    'use strict';

    var Router = Ember['default'].Router.extend({
        location: config['default'].locationType
    });

    exports['default'] = Router.map(function () {
        this.route('login');

        this.resource('me', function () {
            this.route('connections');
            this.route('projects');
        });

        this.resource('profile', { path: 'profile/:user_id' }, function () {
            this.route('connections');
            this.route('projects');
        });

        this.resource('project', { path: 'project/:project_id' }, function () {
            this.route('matching');
        });
    });

});
define('xtalus/routes/application', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ApplicationRoute = Ember['default'].Route.extend({

        beforeModel: function beforeModel() {
            if (!$ISIS.getCookie('auth')) {
                this.transitionTo('login');
            }
        },

        model: function model() {
            if ($ISIS.getCookie('auth')) {
                return $ISIS.init().then(function (isis) {
                    return isis.activePerson.invoke().then(function (activePerson) {
                        var picture = activePerson.picture.split(':');
                        var fullname = activePerson.firstName + ' ' + activePerson.lastName;
                        if (activePerson.middleName) {
                            fullname = activePerson.firstName + ' ' + activePerson.middleName + ' ' + activePerson.lastName;
                        }
                        activePerson.profilePicture = 'data:image/png;base64,' + picture[2];
                        activePerson.fullname = fullname;
                        return activePerson;
                    });
                });
            }
        },

        actions: {
            getProject: function getProject(projectIDs) {
                this.transitionTo('project', projectIDs);
            },

            getProfile: function getProfile(userID) {
                this.transitionTo('profile', userID);
            } }

    });

    exports['default'] = ApplicationRoute;

});
define('xtalus/routes/login', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var LoginRoute = Ember['default'].Route.extend({

        beforeModel: function beforeModel() {
            if ($ISIS.getCookie('auth')) {
                this.transitionTo('me');
            }
        } });

    exports['default'] = LoginRoute;

});
define('xtalus/routes/me', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var MeRoute = Ember['default'].Route.extend({

        beforeModel: function beforeModel() {
            //if(this.controller) this.controller.init();
            if (!$ISIS.getCookie('auth')) {
                this.transitionTo('login');
            }
        },

        model: function model() {
            return this.modelFor('application');
        },

        setupController: function setupController(controller, model) {
            controller.set('activePerson', model);
        },

        actions: {
            logout: function logout() {
                $ISIS.auth.logout();
                this.refresh();
            } }
    });

    exports['default'] = MeRoute;

});
define('xtalus/routes/me/connections', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var MeConnectionsRoute = Ember['default'].Route.extend({
        model: function model() {
            var activePerson = this.modelFor('me');
            return this.initConnections(activePerson);
        },

        setupController: function setupController(controller, connections) {
            controller.setProperties({
                connections: connections,
                connectionCount: connections.length });
        },

        initConnections: function initConnections(person) {
            return person.collectPersonalContacts.extract().then(function (rawdata) {
                var connections = [];
                $.each(rawdata, function (i, connectiondata) {
                    connectiondata.fullname = connectiondata.contactPerson.title;
                    connections.push(connectiondata);
                });
                return connections;
            }).then(this.getConnectionDetails);
        },

        getConnectionDetails: function getConnectionDetails(connections) {
            var connectiondata = connectiondata;
            var a_promises = [];
            $.each(connections, function (i, connectiondata) {

                a_promises.push($ISIS.init(connectiondata.contactPerson.href).then(function (connection) {
                    var picture = connection.picture.split(':');
                    connection.profilePicture = 'data:image/png;base64,' + picture[2];
                    connection.fullname = connectiondata.fullname;

                    return connection;
                }));
            });

            return Ember['default'].RSVP.all(a_promises);
        } });

    exports['default'] = MeConnectionsRoute;

});
define('xtalus/routes/me/index', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var MeIndexRoute = Ember['default'].Route.extend({
        model: function model() {
            var activePerson = this.modelFor('me');
            return activePerson.collectSupplies.extract().then(function (result) {
                return result[0].collectSupplyProfiles.extract().then(function (result) {
                    return result[0].collectProfileElements.extract();
                });
            });
        },

        setupController: function setupController(controller, supplies) {
            var activePerson = this.modelFor('me');
            var picture = activePerson.picture.split(':');

            controller.setProperties({
                birthdate: activePerson.dateOfBirth,
                roles: activePerson.roles,
                fullname: activePerson.fullname,
                profilePicture: 'data:image/png;base64,' + picture[2] });

            $.each(supplies, function (i, supply) {

                if (supply.profileElementDescription === 'PASSION_ELEMENT') {
                    controller.set('passion', supply.textValue);
                }

                if (supply.profileElementDescription === 'QUALITY_TAGS_ELEMENT') {
                    supply.collectTagHolders.extract().then(function (result) {
                        var qualities = [];
                        $.each(result, function (i, tagholder) {
                            qualities.push(tagholder.tag.title);
                        });
                        controller.set('qualities', qualities);
                    });
                }
            });
        } });

    exports['default'] = MeIndexRoute;

});
define('xtalus/routes/me/projects', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var MeProjectsRoute = Ember['default'].Route.extend({

        model: function model() {
            var activePerson = this.modelFor('me');
            return activePerson.collectDemands.extract();
        },

        setupController: function setupController(controller, model) {
            controller.setProperties({
                activePerson: this.modelFor('me'),
                demands: model,
                projectCount: model.length });
        },

        actions: {
            refreshDemands: function refreshDemands() {
                this.refresh();
            } }
    });

    exports['default'] = MeProjectsRoute;

});
define('xtalus/routes/profile', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProfileRoute = Ember['default'].Route.extend({

        model: function model(params) {
            if (params.user_id) {
                return $ISIS.init().then(function (isis) {
                    return isis.findPersonByUniqueId.invoke({
                        uUID: params.user_id });
                });
            } else {
                return params;
            }
        },

        setupController: function setupController(controller, model) {
            console.log(model);
            controller.set('activePerson', this.modelFor('application'));
        }
    });

    exports['default'] = ProfileRoute;

});
define('xtalus/routes/profile/connections', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProfileConnectionsRoute = Ember['default'].Route.extend({

        model: function model() {
            var profile = this.modelFor('profile');
            return this.initConnections(profile);
        },

        setupController: function setupController(controller, connections) {
            controller.setProperties({
                connections: connections,
                connectionCount: connections.length });
        },

        initConnections: function initConnections(person) {
            return person.collectPersonalContacts.extract().then(function (rawdata) {
                var connections = [];
                console.log(rawdata);
                $.each(rawdata, function (i, connectiondata) {
                    if (connectiondata.contactPerson) {
                        connectiondata.fullname = connectiondata.contactPerson.title;
                        connections.push(connectiondata);
                    }
                });
                return connections;
            }).then(this.getConnectionDetails);
        },

        getConnectionDetails: function getConnectionDetails(connections) {
            var connectiondata = connectiondata;
            var a_promises = [];
            $.each(connections, function (i, connectiondata) {

                a_promises.push($ISIS.init(connectiondata.contactPerson.href).then(function (connection) {
                    var picture = connection.picture.split(':');
                    connection.profilePicture = 'data:image/png;base64,' + picture[2];
                    connection.fullname = connectiondata.fullname;

                    return connection;
                }));
            });
            return Ember['default'].RSVP.all(a_promises);
        } });

    exports['default'] = ProfileConnectionsRoute;

});
define('xtalus/routes/profile/index', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProfileIndexRoute = Ember['default'].Route.extend({
        model: function model() {
            console.log(this.modelFor('application'));
            return {
                activePerson: this.modelFor('application'),
                profile: this.modelFor('profile')
            };
        },

        setupController: function setupController(controller, model) {
            var activePerson = model.activePerson;
            var profile = model.profile;

            var picture = profile.picture.split(':');
            var fullname = profile.firstName + ' ' + profile.lastName;
            if (profile.middleName) {
                fullname = profile.firstName + ' ' + profile.middleName + ' ' + profile.lastName;
            }

            controller.setProperties({
                activePerson: activePerson,
                fullname: fullname,
                birthdate: profile.dateOfBirth,
                roles: profile.roles,
                profilePicture: 'data:image/png;base64,' + picture[2] });

            profile.collectSupplies.extract().then(function (result) {
                return result[0].collectSupplyProfiles.extract().then(function (result) {
                    return result[0].collectProfileElements.extract();
                });
            }).then(function (supplies) {

                $.each(supplies, function (i, supply) {

                    if (supply.profileElementDescription === 'PASSION_ELEMENT') {
                        controller.set('passion', supply.textValue);
                    }

                    if (supply.profileElementDescription === 'QUALITY_TAGS_ELEMENT') {
                        supply.collectTagHolders.extract().then(function (result) {
                            var qualities = [];
                            $.each(result, function (i, tagholder) {
                                qualities.push(tagholder.tag.title);
                            });
                            controller.set('qualities', qualities);
                        });
                    }
                });
            });
        } });

    exports['default'] = ProfileIndexRoute;

});
define('xtalus/routes/profile/projects', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProfileProjectsRoute = Ember['default'].Route.extend({

        model: function model() {
            var activePerson = this.modelFor('profile');
            return activePerson.collectDemands.extract();
        },

        setupController: function setupController(controller, model) {
            controller.setProperties({
                activePerson: this.modelFor('profile'),
                demands: model,
                projectCount: model.length });
        },

        actions: {
            refreshDemands: function refreshDemands() {
                this.refresh();
            } }
    });

    exports['default'] = ProfileProjectsRoute;

});
define('xtalus/routes/project', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProjectRoute = Ember['default'].Route.extend({

        model: function model(params) {
            if (params.project_id) {
                return $ISIS.init().then(function (isis) {
                    return isis.findDemandByUniqueId.invoke({
                        uUID: params.project_id }).then(function (project) {
                        return $ISIS.init(project.demandOwner.href).then(function (person) {
                            var picture = person.picture.split(':');
                            person.profilePicture = 'data:image/png;base64,' + picture[2], project.owner = person;
                            return project;
                        });
                    });
                });
            } else {
                return params;
            }
        },

        setupController: function setupController(controller, model) {
            controller.set('activePerson', this.modelFor('application'));
        }
    });

    exports['default'] = ProjectRoute;

});
define('xtalus/routes/project/index', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProjectIndexRoute = Ember['default'].Route.extend({
        model: function model() {
            var project = this.modelFor('project');
            var modelObj = {
                activePerson: this.modelFor('application'),
                project: project
            };

            return project.collectDemandProfiles.extract().then(function (matchingProfiles) {
                modelObj.matchingProfiles = matchingProfiles;
                return modelObj;
            });
        },

        setupController: function setupController(controller, model) {
            var activePerson = model.activePerson;
            var project = model.project;
            var matchingProfiles = [];

            $.each(model.matchingProfiles, function (i, profile) {
                matchingProfiles.push({
                    name: profile.profileName
                });
            });

            console.log(project.owner.profilePicture);

            controller.setProperties({
                title: 'Schilder project',
                ownerName: project.demandOwner.title,
                ownerProfilePicture: project.owner.profilePicture,
                description: project.demandDescription,
                matchingProfiles: matchingProfiles,
                startdate: project.demandOrSupplyProfileStartDate,
                enddate: project.demandOrSupplyProfileEndDate,
                summary: project.demandSummary,
                story: project.demandStory });
        } });

    exports['default'] = ProjectIndexRoute;

});
define('xtalus/routes/project/matching', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProjectMatchingRoute = Ember['default'].Route.extend({
        model: function model() {
            var project = this.modelFor('project');
            var modelObj = {
                activePerson: this.modelFor('application'),
                project: project
            };

            return project.collectDemandProfiles.extract().then(function (matchingProfiles) {
                modelObj.matchingProfiles = matchingProfiles;
                return modelObj;
            });
        },

        setupController: function setupController(controller, model) {
            var activePerson = model.activePerson;
            var project = model.project;
            var matchingProfiles = {
                total: model.matchingProfiles.length,
                list: []
            };

            $.each(model.matchingProfiles, function (i, profile) {
                matchingProfiles.list.push({
                    name: profile.profileName
                });
            });

            console.log(matchingProfiles);

            controller.setProperties({
                title: 'Schilder project',
                ownerProfilePicture: project.owner.profilePicture,
                description: project.demandDescription,
                matchingProfiles: matchingProfiles,
                startdate: project.demandOrSupplyProfileStartDate,
                enddate: project.demandOrSupplyProfileEndDate,
                summary: project.demandSummary,
                story: project.demandStory });
        } });

    exports['default'] = ProjectMatchingRoute;

});
define('xtalus/templates/layout/main', ['exports'], function (exports) {

  'use strict';

  exports['default'] = Ember.HTMLBars.template((function() {
    var child0 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createElement("img");
          dom.setAttribute(el1,"id","logo");
          dom.setAttribute(el1,"src","assets/images/logo.png");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child1 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createElement("i");
          dom.setAttribute(el1,"class","fa fa-bars");
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("Projecten");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child2 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createElement("i");
          dom.setAttribute(el1,"class","fa fa-heart");
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("Netwerk");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child3 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createElement("i");
          dom.setAttribute(el1,"class","fa fa-user");
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("Profiel");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child4 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Intranet GGnet");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child5 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Ecologische auto");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child6 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Duurzaam kantoor");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child7 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Meer");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    return {
      isHTMLBars: true,
      revision: "Ember@1.11.1",
      blockParams: 0,
      cachedFragment: null,
      hasRendered: false,
      build: function build(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("header");
        dom.setAttribute(el1,"id","top-bar");
        var el2 = dom.createTextNode("\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createComment("");
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("input");
        dom.setAttribute(el2,"id","search-field");
        dom.setAttribute(el2,"type","text");
        dom.setAttribute(el2,"placeholder","Zoeken");
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("button");
        dom.setAttribute(el2,"id","profile-nav-toggle-btn");
        var el3 = dom.createTextNode("⌄");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("span");
        dom.setAttribute(el2,"id","profile-username");
        var el3 = dom.createTextNode("Logout ");
        dom.appendChild(el2, el3);
        var el3 = dom.createComment("");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("img");
        dom.setAttribute(el2,"id","profilePicture");
        dom.setAttribute(el2,"width","35");
        dom.setAttribute(el2,"height","35");
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n\n");
        dom.appendChild(el0, el1);
        var el1 = dom.createElement("aside");
        dom.setAttribute(el1,"id","main-aside");
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createComment("<button id=\"toggle-main-aside\" {{action \"collapseAside\"}}> <span>&lt;</span> </button>");
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("nav");
        dom.setAttribute(el2,"id","main-nav");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("ul");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("hr");
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h3");
        var el4 = dom.createTextNode("Projecten");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("ul");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createComment("");
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("hr");
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n\n");
        dom.appendChild(el0, el1);
        var el1 = dom.createElement("section");
        dom.setAttribute(el1,"id","main-wrapper");
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createComment("");
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      render: function render(context, env, contextualElement) {
        var dom = env.dom;
        var hooks = env.hooks, block = hooks.block, element = hooks.element, content = hooks.content, get = hooks.get, concat = hooks.concat, attribute = hooks.attribute;
        dom.detectNamespace(contextualElement);
        var fragment;
        if (env.useFragmentCache && dom.canClone) {
          if (this.cachedFragment === null) {
            fragment = this.build(dom);
            if (this.hasRendered) {
              this.cachedFragment = fragment;
            } else {
              this.hasRendered = true;
            }
          }
          if (this.cachedFragment) {
            fragment = dom.cloneNode(this.cachedFragment, true);
          }
        } else {
          fragment = this.build(dom);
        }
        var element0 = dom.childAt(fragment, [0]);
        var element1 = dom.childAt(element0, [7]);
        var element2 = dom.childAt(element0, [9]);
        var element3 = dom.childAt(fragment, [2, 3]);
        var element4 = dom.childAt(element3, [1]);
        var element5 = dom.childAt(element3, [7]);
        var morph0 = dom.createMorphAt(element0,1,1);
        var morph1 = dom.createMorphAt(element1,1,1);
        var attrMorph0 = dom.createAttrMorph(element2, 'src');
        var morph2 = dom.createMorphAt(dom.childAt(element4, [1]),0,0);
        var morph3 = dom.createMorphAt(dom.childAt(element4, [3]),0,0);
        var morph4 = dom.createMorphAt(dom.childAt(element4, [5]),0,0);
        var morph5 = dom.createMorphAt(dom.childAt(element5, [1]),0,0);
        var morph6 = dom.createMorphAt(dom.childAt(element5, [3]),0,0);
        var morph7 = dom.createMorphAt(dom.childAt(element5, [5]),0,0);
        var morph8 = dom.createMorphAt(element3,9,9);
        var morph9 = dom.createMorphAt(dom.childAt(fragment, [4]),1,1);
        block(env, morph0, context, "link-to", ["index"], {}, child0, null);
        element(env, element1, context, "action", ["logout"], {});
        content(env, morph1, context, "activePerson.fullname");
        attribute(env, attrMorph0, element2, "src", concat(env, [get(env, context, "activePerson.profilePicture")]));
        block(env, morph2, context, "link-to", ["me.projects"], {}, child1, null);
        block(env, morph3, context, "link-to", ["me.connections"], {}, child2, null);
        block(env, morph4, context, "link-to", ["me.index"], {}, child3, null);
        block(env, morph5, context, "link-to", ["me.projects"], {}, child4, null);
        block(env, morph6, context, "link-to", ["me.projects"], {}, child5, null);
        block(env, morph7, context, "link-to", ["me.projects"], {}, child6, null);
        block(env, morph8, context, "link-to", ["me.projects"], {}, child7, null);
        content(env, morph9, context, "yield");
        return fragment;
      }
    };
  }()));

});
define('xtalus/templates/login', ['exports'], function (exports) {

  'use strict';

  exports['default'] = Ember.HTMLBars.template((function() {
    return {
      isHTMLBars: true,
      revision: "Ember@1.11.1",
      blockParams: 0,
      cachedFragment: null,
      hasRendered: false,
      build: function build(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("section");
        dom.setAttribute(el1,"id","page");
        dom.setAttribute(el1,"class","login");
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("header");
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h1");
        var el4 = dom.createTextNode("Xtalus");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h3");
        var el4 = dom.createTextNode("Het zakelijke matching platform voor studenten, zzp'ers en professionals");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("section");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("img");
        dom.setAttribute(el3,"src","assets/images/login-header-bg.jpg");
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("p");
        dom.setAttribute(el3,"class","message");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("form");
        dom.setAttribute(el3,"method","post");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("button");
        dom.setAttribute(el4,"type","submit");
        dom.setAttribute(el4,"class","btn");
        var el5 = dom.createTextNode("Inloggen");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      render: function render(context, env, contextualElement) {
        var dom = env.dom;
        var hooks = env.hooks, content = hooks.content, element = hooks.element, get = hooks.get, inline = hooks.inline;
        dom.detectNamespace(contextualElement);
        var fragment;
        if (env.useFragmentCache && dom.canClone) {
          if (this.cachedFragment === null) {
            fragment = this.build(dom);
            if (this.hasRendered) {
              this.cachedFragment = fragment;
            } else {
              this.hasRendered = true;
            }
          }
          if (this.cachedFragment) {
            fragment = dom.cloneNode(this.cachedFragment, true);
          }
        } else {
          fragment = this.build(dom);
        }
        var element0 = dom.childAt(fragment, [0, 3]);
        var element1 = dom.childAt(element0, [5]);
        var morph0 = dom.createMorphAt(dom.childAt(element0, [3]),0,0);
        var morph1 = dom.createMorphAt(element1,1,1);
        var morph2 = dom.createMorphAt(element1,3,3);
        content(env, morph0, context, "message");
        element(env, element1, context, "action", ["login"], {"on": "submit"});
        inline(env, morph1, context, "input", [], {"value": get(env, context, "username"), "type": "text", "placeholder": "gebruikersnaam"});
        inline(env, morph2, context, "input", [], {"value": get(env, context, "password"), "type": "password", "placeholder": "wachtwoord"});
        return fragment;
      }
    };
  }()));

});
define('xtalus/templates/me', ['exports'], function (exports) {

  'use strict';

  exports['default'] = Ember.HTMLBars.template((function() {
    return {
      isHTMLBars: true,
      revision: "Ember@1.11.1",
      blockParams: 0,
      cachedFragment: null,
      hasRendered: false,
      build: function build(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createComment("");
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      render: function render(context, env, contextualElement) {
        var dom = env.dom;
        var hooks = env.hooks, content = hooks.content;
        dom.detectNamespace(contextualElement);
        var fragment;
        if (env.useFragmentCache && dom.canClone) {
          if (this.cachedFragment === null) {
            fragment = this.build(dom);
            if (this.hasRendered) {
              this.cachedFragment = fragment;
            } else {
              this.hasRendered = true;
            }
          }
          if (this.cachedFragment) {
            fragment = dom.cloneNode(this.cachedFragment, true);
          }
        } else {
          fragment = this.build(dom);
        }
        var morph0 = dom.createMorphAt(fragment,0,0,contextualElement);
        dom.insertBoundary(fragment, 0);
        content(env, morph0, context, "outlet");
        return fragment;
      }
    };
  }()));

});
define('xtalus/templates/me/connections', ['exports'], function (exports) {

  'use strict';

  exports['default'] = Ember.HTMLBars.template((function() {
    var child0 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Alle connecties");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child1 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("            ");
          dom.appendChild(el0, el1);
          var el1 = dom.createElement("tr");
          var el2 = dom.createTextNode("\n                ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("td");
          var el3 = dom.createElement("div");
          dom.setAttribute(el3,"class","profilePicture");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode(" ");
          dom.appendChild(el2, el3);
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n                ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("td");
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n                ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("td");
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n            ");
          dom.appendChild(el1, el2);
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          var hooks = env.hooks, get = hooks.get, element = hooks.element, concat = hooks.concat, attribute = hooks.attribute, content = hooks.content;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          var element0 = dom.childAt(fragment, [1]);
          var element1 = dom.childAt(element0, [1]);
          var element2 = dom.childAt(element1, [0]);
          var attrMorph0 = dom.createAttrMorph(element2, 'style');
          var morph0 = dom.createMorphAt(element1,2,2);
          var morph1 = dom.createMorphAt(dom.childAt(element0, [3]),0,0);
          var morph2 = dom.createMorphAt(dom.childAt(element0, [5]),0,0);
          element(env, element0, context, "action", ["showConnectionDetails", get(env, context, "connection")], {});
          attribute(env, attrMorph0, element2, "style", concat(env, ["background-image: url(", get(env, context, "connection.profilePicture"), ") "]));
          content(env, morph0, context, "connection.fullname");
          content(env, morph1, context, "connection.branch");
          content(env, morph2, context, "connection.roles");
          return fragment;
        }
      };
    }());
    return {
      isHTMLBars: true,
      revision: "Ember@1.11.1",
      blockParams: 0,
      cachedFragment: null,
      hasRendered: false,
      build: function build(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("section");
        dom.setAttribute(el1,"id","page");
        dom.setAttribute(el1,"class","network");
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("header");
        dom.setAttribute(el2,"id","sub-bar");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createTextNode("Netwerk");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("nav");
        dom.setAttribute(el3,"id","sub-nav");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("ul");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("section");
        dom.setAttribute(el2,"id","connections");
        dom.setAttribute(el2,"class","list");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h5");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode(" connecties");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("table");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("tr");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("th");
        var el6 = dom.createTextNode("Naam");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("th");
        var el6 = dom.createTextNode("Branch");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("th");
        var el6 = dom.createTextNode("Rol");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n\n");
        dom.appendChild(el3, el4);
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("aside");
        dom.setAttribute(el2,"id","connection-details");
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("header");
        dom.setAttribute(el3,"class","profilePicture");
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h5");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("p");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("button");
        var el4 = dom.createTextNode("Bekijk profiel");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("button");
        var el4 = dom.createTextNode("sluiten");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      render: function render(context, env, contextualElement) {
        var dom = env.dom;
        var hooks = env.hooks, block = hooks.block, content = hooks.content, get = hooks.get, concat = hooks.concat, attribute = hooks.attribute, element = hooks.element;
        dom.detectNamespace(contextualElement);
        var fragment;
        if (env.useFragmentCache && dom.canClone) {
          if (this.cachedFragment === null) {
            fragment = this.build(dom);
            if (this.hasRendered) {
              this.cachedFragment = fragment;
            } else {
              this.hasRendered = true;
            }
          }
          if (this.cachedFragment) {
            fragment = dom.cloneNode(this.cachedFragment, true);
          }
        } else {
          fragment = this.build(dom);
        }
        var element3 = dom.childAt(fragment, [0]);
        var element4 = dom.childAt(element3, [3]);
        var element5 = dom.childAt(element3, [5]);
        var element6 = dom.childAt(element5, [1]);
        var element7 = dom.childAt(element5, [9]);
        var element8 = dom.childAt(element5, [11]);
        var morph0 = dom.createMorphAt(dom.childAt(element3, [1, 3, 1, 1]),0,0);
        var morph1 = dom.createMorphAt(dom.childAt(element4, [1]),0,0);
        var morph2 = dom.createMorphAt(dom.childAt(element4, [3]),3,3);
        var attrMorph0 = dom.createAttrMorph(element6, 'style');
        var morph3 = dom.createMorphAt(dom.childAt(element5, [3]),0,0);
        var morph4 = dom.createMorphAt(dom.childAt(element5, [5]),0,0);
        var morph5 = dom.createMorphAt(dom.childAt(element5, [7]),0,0);
        block(env, morph0, context, "link-to", ["me.connections"], {}, child0, null);
        content(env, morph1, context, "connectionCount");
        block(env, morph2, context, "each", [get(env, context, "connections")], {"keyword": "connection"}, child1, null);
        attribute(env, attrMorph0, element6, "style", concat(env, ["background-image: url(", get(env, context, "selectedPerson.profilePicture"), ") "]));
        content(env, morph3, context, "selectedPerson.fullname");
        content(env, morph4, context, "selectedPerson.roles");
        content(env, morph5, context, "selectedPerson.dateOfBirth");
        element(env, element7, context, "action", ["getProfile", get(env, context, "selectedPerson.uniqueItemId")], {});
        element(env, element8, context, "action", ["hideConnectionDetails"], {});
        return fragment;
      }
    };
  }()));

});
define('xtalus/templates/me/index', ['exports'], function (exports) {

  'use strict';

  exports['default'] = Ember.HTMLBars.template((function() {
    var child0 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Algemeen");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child1 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("                    ");
          dom.appendChild(el0, el1);
          var el1 = dom.createElement("li");
          var el2 = dom.createComment("");
          dom.appendChild(el1, el2);
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          var hooks = env.hooks, content = hooks.content;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          var morph0 = dom.createMorphAt(dom.childAt(fragment, [1]),0,0);
          content(env, morph0, context, "quality");
          return fragment;
        }
      };
    }());
    return {
      isHTMLBars: true,
      revision: "Ember@1.11.1",
      blockParams: 0,
      cachedFragment: null,
      hasRendered: false,
      build: function build(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("section");
        dom.setAttribute(el1,"id","page");
        dom.setAttribute(el1,"class","profile");
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("header");
        dom.setAttribute(el2,"id","sub-bar");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("img");
        dom.setAttribute(el3,"class","profile-image");
        dom.setAttribute(el3,"width","100");
        dom.setAttribute(el3,"height","100");
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("nav");
        dom.setAttribute(el3,"id","sub-nav");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("ul");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("aside");
        dom.setAttribute(el2,"id","user-info");
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h5");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("ul");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createTextNode("Locatie: ");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createTextNode("Geboortedatum: ");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("ul");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createElement("strong");
        var el6 = dom.createTextNode("Beschikbaarheid");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createTextNode("1 april t/m 30 maart");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createTextNode("maandag");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createTextNode("dinsdag");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("section");
        dom.setAttribute(el2,"id","profile-data");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("section");
        dom.setAttribute(el3,"id","passion");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("h3");
        var el5 = dom.createTextNode("Passie");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("p");
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("section");
        dom.setAttribute(el3,"id","qualities");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("h3");
        var el5 = dom.createTextNode("Kwaliteiten");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("ul");
        var el5 = dom.createTextNode("\n");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n\n\n\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      render: function render(context, env, contextualElement) {
        var dom = env.dom;
        var hooks = env.hooks, get = hooks.get, concat = hooks.concat, attribute = hooks.attribute, content = hooks.content, block = hooks.block;
        dom.detectNamespace(contextualElement);
        var fragment;
        if (env.useFragmentCache && dom.canClone) {
          if (this.cachedFragment === null) {
            fragment = this.build(dom);
            if (this.hasRendered) {
              this.cachedFragment = fragment;
            } else {
              this.hasRendered = true;
            }
          }
          if (this.cachedFragment) {
            fragment = dom.cloneNode(this.cachedFragment, true);
          }
        } else {
          fragment = this.build(dom);
        }
        var element0 = dom.childAt(fragment, [0]);
        var element1 = dom.childAt(element0, [1]);
        var element2 = dom.childAt(element1, [1]);
        var element3 = dom.childAt(element0, [3]);
        var element4 = dom.childAt(element3, [5]);
        var element5 = dom.childAt(element0, [5]);
        var attrMorph0 = dom.createAttrMorph(element2, 'src');
        var morph0 = dom.createMorphAt(dom.childAt(element1, [3]),0,0);
        var morph1 = dom.createMorphAt(dom.childAt(element1, [5, 1, 1]),0,0);
        var morph2 = dom.createMorphAt(dom.childAt(element3, [1]),0,0);
        var morph3 = dom.createMorphAt(dom.childAt(element3, [3]),0,0);
        var morph4 = dom.createMorphAt(dom.childAt(element4, [1]),1,1);
        var morph5 = dom.createMorphAt(dom.childAt(element4, [3]),1,1);
        var morph6 = dom.createMorphAt(dom.childAt(element5, [1, 3]),0,0);
        var morph7 = dom.createMorphAt(dom.childAt(element5, [3, 3]),1,1);
        attribute(env, attrMorph0, element2, "src", concat(env, [get(env, context, "profilePicture")]));
        content(env, morph0, context, "fullname");
        block(env, morph1, context, "link-to", ["me"], {}, child0, null);
        content(env, morph2, context, "fullname");
        content(env, morph3, context, "roles");
        content(env, morph4, context, "location");
        content(env, morph5, context, "birthdate");
        content(env, morph6, context, "passion");
        block(env, morph7, context, "each", [get(env, context, "qualities")], {"keyword": "quality"}, child1, null);
        return fragment;
      }
    };
  }()));

});
define('xtalus/templates/me/projects', ['exports'], function (exports) {

  'use strict';

  exports['default'] = Ember.HTMLBars.template((function() {
    var child0 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Alle projecten");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child1 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("\n                    ");
          dom.appendChild(el0, el1);
          var el1 = dom.createElement("section");
          dom.setAttribute(el1,"class","demand");
          var el2 = dom.createTextNode("\n                        ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("img");
          dom.setAttribute(el2,"src","assets/images/plant.jpg");
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n                        ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("section");
          dom.setAttribute(el2,"class","description");
          var el3 = dom.createTextNode("\n                            ");
          dom.appendChild(el2, el3);
          var el3 = dom.createElement("h4");
          var el4 = dom.createComment("");
          dom.appendChild(el3, el4);
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n                            ");
          dom.appendChild(el2, el3);
          var el3 = dom.createComment(" <span>{{demand.demandOrSupplyProfileStartDate}} - {{demand.demandOrSupplyProfileEndDate}}</span> ");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n                            ");
          dom.appendChild(el2, el3);
          var el3 = dom.createComment(" <p>{{demand.demandSummary}}</p>");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n                            ");
          dom.appendChild(el2, el3);
          var el3 = dom.createElement("p");
          var el4 = dom.createComment("");
          dom.appendChild(el3, el4);
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n                            ");
          dom.appendChild(el2, el3);
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n                        ");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n                    ");
          dom.appendChild(el1, el2);
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("\n\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          var hooks = env.hooks, get = hooks.get, element = hooks.element, content = hooks.content;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          var element0 = dom.childAt(fragment, [1]);
          var element1 = dom.childAt(element0, [3]);
          var morph0 = dom.createMorphAt(dom.childAt(element1, [1]),0,0);
          var morph1 = dom.createMorphAt(dom.childAt(element1, [7]),0,0);
          var morph2 = dom.createMorphAt(element1,9,9);
          element(env, element0, context, "action", ["showDetails", get(env, context, "demand.uniqueItemId")], {});
          content(env, morph0, context, "demand.demandDescription");
          content(env, morph1, context, "demand.demandOwner.title");
          content(env, morph2, context, "demand.uniqueItemID");
          return fragment;
        }
      };
    }());
    return {
      isHTMLBars: true,
      revision: "Ember@1.11.1",
      blockParams: 0,
      cachedFragment: null,
      hasRendered: false,
      build: function build(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("section");
        dom.setAttribute(el1,"id","page");
        dom.setAttribute(el1,"class","projects");
        var el2 = dom.createTextNode("\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("header");
        dom.setAttribute(el2,"id","sub-bar");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("button");
        var el4 = dom.createTextNode("Nieuw project");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createTextNode("Projecten");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("nav");
        dom.setAttribute(el3,"id","sub-nav");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("ul");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("section");
        dom.setAttribute(el2,"id","content");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("section");
        dom.setAttribute(el3,"id","content-wrapper");
        var el4 = dom.createTextNode("\n\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("aside");
        dom.setAttribute(el4,"id","content-aside");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("header");
        dom.setAttribute(el5,"class","projectPicture");
        dom.setAttribute(el5,"style","background-image: url('assets/images/plant.jpg') ");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("h4");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("h5");
        var el6 = dom.createTextNode("samenvatting");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("p");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("h5");
        var el6 = dom.createTextNode("verhaal");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("p");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("button");
        var el6 = dom.createTextNode("Sluiten");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("br");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("br");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("button");
        var el6 = dom.createTextNode("Bekijk project");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("header");
        dom.setAttribute(el4,"id","content-bar");
        var el5 = dom.createTextNode("\n\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode(" lopende projecten\n\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("ul");
        var el6 = dom.createTextNode("\n                    ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("li");
        var el7 = dom.createElement("button");
        var el8 = dom.createTextNode("1");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n                    ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("li");
        var el7 = dom.createElement("button");
        var el8 = dom.createTextNode("2");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n                    ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("li");
        var el7 = dom.createElement("button");
        var el8 = dom.createTextNode("3");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n                ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("section");
        dom.setAttribute(el4,"id","content");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("section");
        dom.setAttribute(el5,"id","demands");
        var el6 = dom.createTextNode("\n\n");
        dom.appendChild(el5, el6);
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n                ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("section");
        dom.setAttribute(el5,"id","add-project-popup");
        var el6 = dom.createTextNode("\n\n                    ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("section");
        var el7 = dom.createTextNode("\n                        ");
        dom.appendChild(el6, el7);
        var el7 = dom.createElement("button");
        dom.setAttribute(el7,"class","btn close-popup-btn");
        var el8 = dom.createTextNode("X");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        var el7 = dom.createTextNode("\n                        ");
        dom.appendChild(el6, el7);
        var el7 = dom.createElement("header");
        var el8 = dom.createTextNode("\n                            ");
        dom.appendChild(el7, el8);
        var el8 = dom.createElement("h2");
        var el9 = dom.createTextNode("Nieuw project");
        dom.appendChild(el8, el9);
        dom.appendChild(el7, el8);
        var el8 = dom.createTextNode("\n                        ");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        var el7 = dom.createTextNode("\n                        ");
        dom.appendChild(el6, el7);
        var el7 = dom.createElement("form");
        dom.setAttribute(el7,"method","post");
        var el8 = dom.createTextNode("\n                            ");
        dom.appendChild(el7, el8);
        var el8 = dom.createComment("");
        dom.appendChild(el7, el8);
        var el8 = dom.createElement("br");
        dom.appendChild(el7, el8);
        var el8 = dom.createTextNode("\n                            ");
        dom.appendChild(el7, el8);
        var el8 = dom.createComment("");
        dom.appendChild(el7, el8);
        var el8 = dom.createElement("br");
        dom.appendChild(el7, el8);
        var el8 = dom.createTextNode("\n                            ");
        dom.appendChild(el7, el8);
        var el8 = dom.createComment("");
        dom.appendChild(el7, el8);
        var el8 = dom.createElement("br");
        dom.appendChild(el7, el8);
        var el8 = dom.createTextNode("\n\n                            ");
        dom.appendChild(el7, el8);
        var el8 = dom.createElement("button");
        dom.setAttribute(el8,"type","submit");
        dom.setAttribute(el8,"class","btn add-project-btn");
        var el9 = dom.createTextNode("Project toevoegen");
        dom.appendChild(el8, el9);
        dom.appendChild(el7, el8);
        var el8 = dom.createTextNode("\n                        ");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        var el7 = dom.createTextNode("\n\n                    ");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n\n                ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n\n\n\n\n\n\n\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      render: function render(context, env, contextualElement) {
        var dom = env.dom;
        var hooks = env.hooks, element = hooks.element, block = hooks.block, content = hooks.content, get = hooks.get, inline = hooks.inline;
        dom.detectNamespace(contextualElement);
        var fragment;
        if (env.useFragmentCache && dom.canClone) {
          if (this.cachedFragment === null) {
            fragment = this.build(dom);
            if (this.hasRendered) {
              this.cachedFragment = fragment;
            } else {
              this.hasRendered = true;
            }
          }
          if (this.cachedFragment) {
            fragment = dom.cloneNode(this.cachedFragment, true);
          }
        } else {
          fragment = this.build(dom);
        }
        var element2 = dom.childAt(fragment, [0]);
        var element3 = dom.childAt(element2, [1]);
        var element4 = dom.childAt(element3, [1]);
        var element5 = dom.childAt(element2, [3, 1]);
        var element6 = dom.childAt(element5, [1]);
        var element7 = dom.childAt(element6, [13]);
        var element8 = dom.childAt(element6, [17]);
        var element9 = dom.childAt(element5, [5]);
        var element10 = dom.childAt(element9, [5, 1]);
        var element11 = dom.childAt(element10, [1]);
        var element12 = dom.childAt(element10, [5]);
        var morph0 = dom.createMorphAt(dom.childAt(element3, [5, 1, 1]),0,0);
        var morph1 = dom.createMorphAt(dom.childAt(element6, [3]),0,0);
        var morph2 = dom.createMorphAt(dom.childAt(element6, [7]),0,0);
        var morph3 = dom.createMorphAt(dom.childAt(element6, [11]),0,0);
        var morph4 = dom.createMorphAt(dom.childAt(element5, [3]),1,1);
        var morph5 = dom.createMorphAt(element9,1,1);
        var morph6 = dom.createMorphAt(dom.childAt(element9, [3]),1,1);
        var morph7 = dom.createMorphAt(element12,1,1);
        var morph8 = dom.createMorphAt(element12,4,4);
        var morph9 = dom.createMorphAt(element12,7,7);
        element(env, element4, context, "action", ["showPopup", "new-project"], {});
        block(env, morph0, context, "link-to", ["me.projects"], {}, child0, null);
        content(env, morph1, context, "selectedDemand.demandDescription");
        content(env, morph2, context, "selectedDemand.demandSummary");
        content(env, morph3, context, "selectedDemand.demandStory");
        element(env, element7, context, "action", ["hideDetails"], {});
        element(env, element8, context, "action", ["getProject", get(env, context, "selectedDemand.uniqueItemId")], {});
        content(env, morph4, context, "projectCount");
        content(env, morph5, context, "outlet");
        block(env, morph6, context, "each", [get(env, context, "demands")], {"keyword": "demand"}, child1, null);
        element(env, element11, context, "action", ["closePopup", "new-project"], {});
        element(env, element12, context, "action", ["createProject"], {"on": "submit"});
        inline(env, morph7, context, "input", [], {"value": get(env, context, "title"), "type": "text", "placeholder": "Beschrijving", "class": "txt-field"});
        inline(env, morph8, context, "textarea", [], {"value": get(env, context, "summary"), "placeholder": "Samenvatting", "class": "txt-area"});
        inline(env, morph9, context, "textarea", [], {"value": get(env, context, "story"), "placeholder": "Verhaal", "class": "txt-area"});
        return fragment;
      }
    };
  }()));

});
define('xtalus/templates/profile', ['exports'], function (exports) {

  'use strict';

  exports['default'] = Ember.HTMLBars.template((function() {
    return {
      isHTMLBars: true,
      revision: "Ember@1.11.1",
      blockParams: 0,
      cachedFragment: null,
      hasRendered: false,
      build: function build(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createComment("");
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      render: function render(context, env, contextualElement) {
        var dom = env.dom;
        var hooks = env.hooks, content = hooks.content;
        dom.detectNamespace(contextualElement);
        var fragment;
        if (env.useFragmentCache && dom.canClone) {
          if (this.cachedFragment === null) {
            fragment = this.build(dom);
            if (this.hasRendered) {
              this.cachedFragment = fragment;
            } else {
              this.hasRendered = true;
            }
          }
          if (this.cachedFragment) {
            fragment = dom.cloneNode(this.cachedFragment, true);
          }
        } else {
          fragment = this.build(dom);
        }
        var morph0 = dom.createMorphAt(fragment,0,0,contextualElement);
        dom.insertBoundary(fragment, 0);
        content(env, morph0, context, "outlet");
        return fragment;
      }
    };
  }()));

});
define('xtalus/templates/profile/connections', ['exports'], function (exports) {

  'use strict';

  exports['default'] = Ember.HTMLBars.template((function() {
    var child0 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Algemeen");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child1 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Connections");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child2 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Projecten");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child3 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("            ");
          dom.appendChild(el0, el1);
          var el1 = dom.createElement("tr");
          var el2 = dom.createTextNode("\n                ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("td");
          var el3 = dom.createElement("div");
          dom.setAttribute(el3,"class","profilePicture");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode(" ");
          dom.appendChild(el2, el3);
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n                ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("td");
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n                ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("td");
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n            ");
          dom.appendChild(el1, el2);
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          var hooks = env.hooks, get = hooks.get, element = hooks.element, concat = hooks.concat, attribute = hooks.attribute, content = hooks.content;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          var element0 = dom.childAt(fragment, [1]);
          var element1 = dom.childAt(element0, [1]);
          var element2 = dom.childAt(element1, [0]);
          var attrMorph0 = dom.createAttrMorph(element2, 'style');
          var morph0 = dom.createMorphAt(element1,2,2);
          var morph1 = dom.createMorphAt(dom.childAt(element0, [3]),0,0);
          var morph2 = dom.createMorphAt(dom.childAt(element0, [5]),0,0);
          element(env, element0, context, "action", ["showConnectionDetails", get(env, context, "connection")], {});
          attribute(env, attrMorph0, element2, "style", concat(env, ["background-image: url(", get(env, context, "connection.profilePicture"), ") "]));
          content(env, morph0, context, "connection.fullname");
          content(env, morph1, context, "connection.branch");
          content(env, morph2, context, "connection.roles");
          return fragment;
        }
      };
    }());
    return {
      isHTMLBars: true,
      revision: "Ember@1.11.1",
      blockParams: 0,
      cachedFragment: null,
      hasRendered: false,
      build: function build(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("section");
        dom.setAttribute(el1,"id","page");
        dom.setAttribute(el1,"class","network");
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("header");
        dom.setAttribute(el2,"id","sub-bar");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createTextNode("Netwerk");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("nav");
        dom.setAttribute(el3,"id","sub-nav");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("ul");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("section");
        dom.setAttribute(el2,"id","connections");
        dom.setAttribute(el2,"class","list");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h5");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode(" connecties");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("table");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("tr");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("th");
        var el6 = dom.createTextNode("Naam");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("th");
        var el6 = dom.createTextNode("Branch");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("th");
        var el6 = dom.createTextNode("Rol");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n\n");
        dom.appendChild(el3, el4);
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("aside");
        dom.setAttribute(el2,"id","connection-details");
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("header");
        dom.setAttribute(el3,"class","profilePicture");
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h5");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("p");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("button");
        var el4 = dom.createTextNode("Bekijk profiel");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("button");
        var el4 = dom.createTextNode("sluiten");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      render: function render(context, env, contextualElement) {
        var dom = env.dom;
        var hooks = env.hooks, block = hooks.block, content = hooks.content, get = hooks.get, concat = hooks.concat, attribute = hooks.attribute, element = hooks.element;
        dom.detectNamespace(contextualElement);
        var fragment;
        if (env.useFragmentCache && dom.canClone) {
          if (this.cachedFragment === null) {
            fragment = this.build(dom);
            if (this.hasRendered) {
              this.cachedFragment = fragment;
            } else {
              this.hasRendered = true;
            }
          }
          if (this.cachedFragment) {
            fragment = dom.cloneNode(this.cachedFragment, true);
          }
        } else {
          fragment = this.build(dom);
        }
        var element3 = dom.childAt(fragment, [0]);
        var element4 = dom.childAt(element3, [1, 3, 1]);
        var element5 = dom.childAt(element3, [3]);
        var element6 = dom.childAt(element3, [5]);
        var element7 = dom.childAt(element6, [1]);
        var element8 = dom.childAt(element6, [9]);
        var element9 = dom.childAt(element6, [11]);
        var morph0 = dom.createMorphAt(dom.childAt(element4, [1]),0,0);
        var morph1 = dom.createMorphAt(dom.childAt(element4, [3]),0,0);
        var morph2 = dom.createMorphAt(dom.childAt(element4, [5]),0,0);
        var morph3 = dom.createMorphAt(dom.childAt(element5, [1]),0,0);
        var morph4 = dom.createMorphAt(dom.childAt(element5, [3]),3,3);
        var attrMorph0 = dom.createAttrMorph(element7, 'style');
        var morph5 = dom.createMorphAt(dom.childAt(element6, [3]),0,0);
        var morph6 = dom.createMorphAt(dom.childAt(element6, [5]),0,0);
        var morph7 = dom.createMorphAt(dom.childAt(element6, [7]),0,0);
        block(env, morph0, context, "link-to", ["profile.index"], {}, child0, null);
        block(env, morph1, context, "link-to", ["profile.connections"], {}, child1, null);
        block(env, morph2, context, "link-to", ["profile.projects"], {}, child2, null);
        content(env, morph3, context, "connectionCount");
        block(env, morph4, context, "each", [get(env, context, "connections")], {"keyword": "connection"}, child3, null);
        attribute(env, attrMorph0, element7, "style", concat(env, ["background-image: url(", get(env, context, "selectedPerson.profilePicture"), ") "]));
        content(env, morph5, context, "selectedPerson.fullname");
        content(env, morph6, context, "selectedPerson.roles");
        content(env, morph7, context, "selectedPerson.dateOfBirth");
        element(env, element8, context, "action", ["getUserProfile", get(env, context, "selectedPerson.uniqueItemId")], {});
        element(env, element9, context, "action", ["hideConnectionDetails"], {});
        return fragment;
      }
    };
  }()));

});
define('xtalus/templates/profile/index', ['exports'], function (exports) {

  'use strict';

  exports['default'] = Ember.HTMLBars.template((function() {
    var child0 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Algemeen");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child1 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Connections");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child2 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Projecten");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child3 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("                    ");
          dom.appendChild(el0, el1);
          var el1 = dom.createElement("li");
          var el2 = dom.createComment("");
          dom.appendChild(el1, el2);
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          var hooks = env.hooks, content = hooks.content;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          var morph0 = dom.createMorphAt(dom.childAt(fragment, [1]),0,0);
          content(env, morph0, context, "quality");
          return fragment;
        }
      };
    }());
    return {
      isHTMLBars: true,
      revision: "Ember@1.11.1",
      blockParams: 0,
      cachedFragment: null,
      hasRendered: false,
      build: function build(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("section");
        dom.setAttribute(el1,"id","page");
        dom.setAttribute(el1,"class","profile");
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("header");
        dom.setAttribute(el2,"id","sub-bar");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("img");
        dom.setAttribute(el3,"class","profile-image");
        dom.setAttribute(el3,"width","100");
        dom.setAttribute(el3,"height","100");
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("nav");
        dom.setAttribute(el3,"id","sub-nav");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("ul");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("aside");
        dom.setAttribute(el2,"id","user-info");
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h5");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("ul");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createTextNode("Locatie: ");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createTextNode("Geboortedatum: ");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("ul");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createElement("strong");
        var el6 = dom.createTextNode("Beschikbaarheid");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createTextNode("1 april t/m 30 maart");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createTextNode("maandag");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createTextNode("dinsdag");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("section");
        dom.setAttribute(el2,"id","profile-data");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("section");
        dom.setAttribute(el3,"id","passion");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("h3");
        var el5 = dom.createTextNode("Passie");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("p");
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("section");
        dom.setAttribute(el3,"id","qualities");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("h3");
        var el5 = dom.createTextNode("Kwaliteiten");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("ul");
        var el5 = dom.createTextNode("\n");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n\n\n\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      render: function render(context, env, contextualElement) {
        var dom = env.dom;
        var hooks = env.hooks, get = hooks.get, concat = hooks.concat, attribute = hooks.attribute, content = hooks.content, block = hooks.block;
        dom.detectNamespace(contextualElement);
        var fragment;
        if (env.useFragmentCache && dom.canClone) {
          if (this.cachedFragment === null) {
            fragment = this.build(dom);
            if (this.hasRendered) {
              this.cachedFragment = fragment;
            } else {
              this.hasRendered = true;
            }
          }
          if (this.cachedFragment) {
            fragment = dom.cloneNode(this.cachedFragment, true);
          }
        } else {
          fragment = this.build(dom);
        }
        var element0 = dom.childAt(fragment, [0]);
        var element1 = dom.childAt(element0, [1]);
        var element2 = dom.childAt(element1, [1]);
        var element3 = dom.childAt(element1, [5, 1]);
        var element4 = dom.childAt(element0, [3]);
        var element5 = dom.childAt(element4, [5]);
        var element6 = dom.childAt(element0, [5]);
        var attrMorph0 = dom.createAttrMorph(element2, 'src');
        var morph0 = dom.createMorphAt(dom.childAt(element1, [3]),0,0);
        var morph1 = dom.createMorphAt(dom.childAt(element3, [1]),0,0);
        var morph2 = dom.createMorphAt(dom.childAt(element3, [3]),0,0);
        var morph3 = dom.createMorphAt(dom.childAt(element3, [5]),0,0);
        var morph4 = dom.createMorphAt(dom.childAt(element4, [1]),0,0);
        var morph5 = dom.createMorphAt(dom.childAt(element4, [3]),0,0);
        var morph6 = dom.createMorphAt(dom.childAt(element5, [1]),1,1);
        var morph7 = dom.createMorphAt(dom.childAt(element5, [3]),1,1);
        var morph8 = dom.createMorphAt(dom.childAt(element6, [1, 3]),0,0);
        var morph9 = dom.createMorphAt(dom.childAt(element6, [3, 3]),1,1);
        attribute(env, attrMorph0, element2, "src", concat(env, [get(env, context, "profilePicture")]));
        content(env, morph0, context, "fullname");
        block(env, morph1, context, "link-to", ["profile"], {}, child0, null);
        block(env, morph2, context, "link-to", ["profile.connections"], {}, child1, null);
        block(env, morph3, context, "link-to", ["profile.projects"], {}, child2, null);
        content(env, morph4, context, "fullname");
        content(env, morph5, context, "roles");
        content(env, morph6, context, "location");
        content(env, morph7, context, "birthdate");
        content(env, morph8, context, "passion");
        block(env, morph9, context, "each", [get(env, context, "qualities")], {"keyword": "quality"}, child3, null);
        return fragment;
      }
    };
  }()));

});
define('xtalus/templates/profile/projects', ['exports'], function (exports) {

  'use strict';

  exports['default'] = Ember.HTMLBars.template((function() {
    var child0 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Alle projecten");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child1 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("\n                    ");
          dom.appendChild(el0, el1);
          var el1 = dom.createElement("section");
          dom.setAttribute(el1,"class","demand");
          var el2 = dom.createTextNode("\n                        ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("img");
          dom.setAttribute(el2,"src","assets/images/plant.jpg");
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n                        ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("section");
          dom.setAttribute(el2,"class","description");
          var el3 = dom.createTextNode("\n                            ");
          dom.appendChild(el2, el3);
          var el3 = dom.createElement("h4");
          var el4 = dom.createComment("");
          dom.appendChild(el3, el4);
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n                            ");
          dom.appendChild(el2, el3);
          var el3 = dom.createComment(" <span>{{demand.demandOrSupplyProfileStartDate}} - {{demand.demandOrSupplyProfileEndDate}}</span> ");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n                            ");
          dom.appendChild(el2, el3);
          var el3 = dom.createComment(" <p>{{demand.demandSummary}}</p>");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n                            ");
          dom.appendChild(el2, el3);
          var el3 = dom.createElement("p");
          var el4 = dom.createComment("");
          dom.appendChild(el3, el4);
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n                            ");
          dom.appendChild(el2, el3);
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n                        ");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n                    ");
          dom.appendChild(el1, el2);
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("\n\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          var hooks = env.hooks, get = hooks.get, element = hooks.element, content = hooks.content;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          var element0 = dom.childAt(fragment, [1]);
          var element1 = dom.childAt(element0, [3]);
          var morph0 = dom.createMorphAt(dom.childAt(element1, [1]),0,0);
          var morph1 = dom.createMorphAt(dom.childAt(element1, [7]),0,0);
          var morph2 = dom.createMorphAt(element1,9,9);
          element(env, element0, context, "action", ["showDetails", get(env, context, "demand.uniqueItemId")], {});
          content(env, morph0, context, "demand.demandDescription");
          content(env, morph1, context, "demand.demandOwner.title");
          content(env, morph2, context, "demand.uniqueItemID");
          return fragment;
        }
      };
    }());
    return {
      isHTMLBars: true,
      revision: "Ember@1.11.1",
      blockParams: 0,
      cachedFragment: null,
      hasRendered: false,
      build: function build(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("section");
        dom.setAttribute(el1,"id","page");
        dom.setAttribute(el1,"class","projects");
        var el2 = dom.createTextNode("\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("header");
        dom.setAttribute(el2,"id","sub-bar");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("button");
        var el4 = dom.createTextNode("Nieuw project");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createTextNode("Projecten");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("nav");
        dom.setAttribute(el3,"id","sub-nav");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("ul");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("section");
        dom.setAttribute(el2,"id","content");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("section");
        dom.setAttribute(el3,"id","content-wrapper");
        var el4 = dom.createTextNode("\n\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("aside");
        dom.setAttribute(el4,"id","content-aside");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("header");
        dom.setAttribute(el5,"class","projectPicture");
        dom.setAttribute(el5,"style","background-image: url('assets/images/plant.jpg') ");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("h4");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("h5");
        var el6 = dom.createTextNode("samenvatting");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("p");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("h5");
        var el6 = dom.createTextNode("verhaal");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("p");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("button");
        var el6 = dom.createTextNode("Sluiten");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("br");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("br");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("button");
        var el6 = dom.createTextNode("Verwijderen");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("header");
        dom.setAttribute(el4,"id","content-bar");
        var el5 = dom.createTextNode("\n\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode(" lopende projecten\n\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("ul");
        var el6 = dom.createTextNode("\n                    ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("li");
        var el7 = dom.createElement("button");
        var el8 = dom.createTextNode("1");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n                    ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("li");
        var el7 = dom.createElement("button");
        var el8 = dom.createTextNode("2");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n                    ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("li");
        var el7 = dom.createElement("button");
        var el8 = dom.createTextNode("3");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n                ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("section");
        dom.setAttribute(el4,"id","content");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("section");
        dom.setAttribute(el5,"id","demands");
        var el6 = dom.createTextNode("\n\n");
        dom.appendChild(el5, el6);
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n                ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("section");
        dom.setAttribute(el5,"id","add-project-popup");
        var el6 = dom.createTextNode("\n\n                    ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("section");
        var el7 = dom.createTextNode("\n                        ");
        dom.appendChild(el6, el7);
        var el7 = dom.createElement("button");
        dom.setAttribute(el7,"class","btn close-popup-btn");
        var el8 = dom.createTextNode("X");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        var el7 = dom.createTextNode("\n                        ");
        dom.appendChild(el6, el7);
        var el7 = dom.createElement("header");
        var el8 = dom.createTextNode("\n                            ");
        dom.appendChild(el7, el8);
        var el8 = dom.createElement("h2");
        var el9 = dom.createTextNode("Nieuw project");
        dom.appendChild(el8, el9);
        dom.appendChild(el7, el8);
        var el8 = dom.createTextNode("\n                        ");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        var el7 = dom.createTextNode("\n                        ");
        dom.appendChild(el6, el7);
        var el7 = dom.createElement("form");
        dom.setAttribute(el7,"method","post");
        var el8 = dom.createTextNode("\n                            ");
        dom.appendChild(el7, el8);
        var el8 = dom.createComment("");
        dom.appendChild(el7, el8);
        var el8 = dom.createElement("br");
        dom.appendChild(el7, el8);
        var el8 = dom.createTextNode("\n                            ");
        dom.appendChild(el7, el8);
        var el8 = dom.createComment("");
        dom.appendChild(el7, el8);
        var el8 = dom.createElement("br");
        dom.appendChild(el7, el8);
        var el8 = dom.createTextNode("\n                            ");
        dom.appendChild(el7, el8);
        var el8 = dom.createComment("");
        dom.appendChild(el7, el8);
        var el8 = dom.createElement("br");
        dom.appendChild(el7, el8);
        var el8 = dom.createTextNode("\n\n                            ");
        dom.appendChild(el7, el8);
        var el8 = dom.createElement("button");
        dom.setAttribute(el8,"type","submit");
        dom.setAttribute(el8,"class","btn add-project-btn");
        var el9 = dom.createTextNode("Project toevoegen");
        dom.appendChild(el8, el9);
        dom.appendChild(el7, el8);
        var el8 = dom.createTextNode("\n                        ");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        var el7 = dom.createTextNode("\n\n                    ");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n\n                ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      render: function render(context, env, contextualElement) {
        var dom = env.dom;
        var hooks = env.hooks, element = hooks.element, block = hooks.block, content = hooks.content, get = hooks.get, inline = hooks.inline;
        dom.detectNamespace(contextualElement);
        var fragment;
        if (env.useFragmentCache && dom.canClone) {
          if (this.cachedFragment === null) {
            fragment = this.build(dom);
            if (this.hasRendered) {
              this.cachedFragment = fragment;
            } else {
              this.hasRendered = true;
            }
          }
          if (this.cachedFragment) {
            fragment = dom.cloneNode(this.cachedFragment, true);
          }
        } else {
          fragment = this.build(dom);
        }
        var element2 = dom.childAt(fragment, [0]);
        var element3 = dom.childAt(element2, [1]);
        var element4 = dom.childAt(element3, [1]);
        var element5 = dom.childAt(element2, [3, 1]);
        var element6 = dom.childAt(element5, [1]);
        var element7 = dom.childAt(element6, [13]);
        var element8 = dom.childAt(element6, [17]);
        var element9 = dom.childAt(element5, [5]);
        var element10 = dom.childAt(element9, [5, 1]);
        var element11 = dom.childAt(element10, [1]);
        var element12 = dom.childAt(element10, [5]);
        var morph0 = dom.createMorphAt(dom.childAt(element3, [5, 1, 1]),0,0);
        var morph1 = dom.createMorphAt(dom.childAt(element6, [3]),0,0);
        var morph2 = dom.createMorphAt(dom.childAt(element6, [7]),0,0);
        var morph3 = dom.createMorphAt(dom.childAt(element6, [11]),0,0);
        var morph4 = dom.createMorphAt(dom.childAt(element5, [3]),1,1);
        var morph5 = dom.createMorphAt(element9,1,1);
        var morph6 = dom.createMorphAt(dom.childAt(element9, [3]),1,1);
        var morph7 = dom.createMorphAt(element12,1,1);
        var morph8 = dom.createMorphAt(element12,4,4);
        var morph9 = dom.createMorphAt(element12,7,7);
        element(env, element4, context, "action", ["showPopup", "new-project"], {});
        block(env, morph0, context, "link-to", ["me.projects"], {}, child0, null);
        content(env, morph1, context, "selectedDemand.demandDescription");
        content(env, morph2, context, "selectedDemand.demandSummary");
        content(env, morph3, context, "selectedDemand.demandStory");
        element(env, element7, context, "action", ["hideDetails"], {});
        element(env, element8, context, "action", ["delProject"], {});
        content(env, morph4, context, "projectCount");
        content(env, morph5, context, "outlet");
        block(env, morph6, context, "each", [get(env, context, "demands")], {"keyword": "demand"}, child1, null);
        element(env, element11, context, "action", ["closePopup", "new-project"], {});
        element(env, element12, context, "action", ["createProject"], {"on": "submit"});
        inline(env, morph7, context, "input", [], {"value": get(env, context, "title"), "type": "text", "placeholder": "Beschrijving", "class": "txt-field"});
        inline(env, morph8, context, "textarea", [], {"value": get(env, context, "summary"), "placeholder": "Samenvatting", "class": "txt-area"});
        inline(env, morph9, context, "textarea", [], {"value": get(env, context, "story"), "placeholder": "Verhaal", "class": "txt-area"});
        return fragment;
      }
    };
  }()));

});
define('xtalus/templates/project', ['exports'], function (exports) {

  'use strict';

  exports['default'] = Ember.HTMLBars.template((function() {
    return {
      isHTMLBars: true,
      revision: "Ember@1.11.1",
      blockParams: 0,
      cachedFragment: null,
      hasRendered: false,
      build: function build(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createComment("");
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      render: function render(context, env, contextualElement) {
        var dom = env.dom;
        var hooks = env.hooks, content = hooks.content;
        dom.detectNamespace(contextualElement);
        var fragment;
        if (env.useFragmentCache && dom.canClone) {
          if (this.cachedFragment === null) {
            fragment = this.build(dom);
            if (this.hasRendered) {
              this.cachedFragment = fragment;
            } else {
              this.hasRendered = true;
            }
          }
          if (this.cachedFragment) {
            fragment = dom.cloneNode(this.cachedFragment, true);
          }
        } else {
          fragment = this.build(dom);
        }
        var morph0 = dom.createMorphAt(fragment,0,0,contextualElement);
        dom.insertBoundary(fragment, 0);
        content(env, morph0, context, "outlet");
        return fragment;
      }
    };
  }()));

});
define('xtalus/templates/project/index', ['exports'], function (exports) {

  'use strict';

  exports['default'] = Ember.HTMLBars.template((function() {
    var child0 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Algemeen");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child1 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Matches");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child2 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("        ");
          dom.appendChild(el0, el1);
          var el1 = dom.createElement("div");
          dom.setAttribute(el1,"style","clear:both;border-bottom:1px solid #ccc; padding: 10px 0;");
          var el2 = dom.createTextNode("\n            ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("div");
          dom.setAttribute(el2,"class","profilePicture");
          var el3 = dom.createTextNode("     ");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n            ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("div");
          var el3 = dom.createElement("strong");
          var el4 = dom.createComment("");
          dom.appendChild(el3, el4);
          dom.appendChild(el2, el3);
          var el3 = dom.createElement("br");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("geen match");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n        ");
          dom.appendChild(el1, el2);
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          var hooks = env.hooks, get = hooks.get, concat = hooks.concat, attribute = hooks.attribute, content = hooks.content;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          var element0 = dom.childAt(fragment, [1]);
          var element1 = dom.childAt(element0, [1]);
          var attrMorph0 = dom.createAttrMorph(element1, 'style');
          var morph0 = dom.createMorphAt(dom.childAt(element0, [3, 0]),0,0);
          attribute(env, attrMorph0, element1, "style", concat(env, ["float:left; margin-right:10px; margin-bottom:10px; background-image: url(", get(env, context, "ownerProfilePicture"), "); width:35px; height:35px;"]));
          content(env, morph0, context, "profile.name");
          return fragment;
        }
      };
    }());
    return {
      isHTMLBars: true,
      revision: "Ember@1.11.1",
      blockParams: 0,
      cachedFragment: null,
      hasRendered: false,
      build: function build(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("section");
        dom.setAttribute(el1,"id","page");
        dom.setAttribute(el1,"class","profile");
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("header");
        dom.setAttribute(el2,"id","sub-bar");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("img");
        dom.setAttribute(el3,"class","profile-image");
        dom.setAttribute(el3,"width","100");
        dom.setAttribute(el3,"height","100");
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createTextNode("Project: ");
        dom.appendChild(el3, el4);
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("nav");
        dom.setAttribute(el3,"id","sub-nav");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("ul");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("aside");
        dom.setAttribute(el2,"id","user-info");
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h5");
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("ul");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createElement("strong");
        var el6 = dom.createTextNode("Beschrijving");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n");
        dom.appendChild(el2, el3);
        var el3 = dom.createComment("");
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("ul");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createElement("strong");
        var el6 = dom.createTextNode("Start- en einddatum");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("li");
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode(" t/m ");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("section");
        dom.setAttribute(el2,"id","profile-data");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("section");
        dom.setAttribute(el3,"id","passion");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("h3");
        var el5 = dom.createTextNode("Samenvatting");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("p");
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("section");
        dom.setAttribute(el3,"id","qualities");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("h3");
        var el5 = dom.createTextNode("verhaal");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("p");
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      render: function render(context, env, contextualElement) {
        var dom = env.dom;
        var hooks = env.hooks, get = hooks.get, concat = hooks.concat, attribute = hooks.attribute, content = hooks.content, block = hooks.block;
        dom.detectNamespace(contextualElement);
        var fragment;
        if (env.useFragmentCache && dom.canClone) {
          if (this.cachedFragment === null) {
            fragment = this.build(dom);
            if (this.hasRendered) {
              this.cachedFragment = fragment;
            } else {
              this.hasRendered = true;
            }
          }
          if (this.cachedFragment) {
            fragment = dom.cloneNode(this.cachedFragment, true);
          }
        } else {
          fragment = this.build(dom);
        }
        var element2 = dom.childAt(fragment, [0]);
        var element3 = dom.childAt(element2, [1]);
        var element4 = dom.childAt(element3, [1]);
        var element5 = dom.childAt(element3, [5, 1]);
        var element6 = dom.childAt(element2, [3]);
        var element7 = dom.childAt(element6, [9, 3]);
        var element8 = dom.childAt(element2, [5]);
        var attrMorph0 = dom.createAttrMorph(element4, 'src');
        var morph0 = dom.createMorphAt(dom.childAt(element3, [3]),1,1);
        var morph1 = dom.createMorphAt(dom.childAt(element5, [1]),0,0);
        var morph2 = dom.createMorphAt(dom.childAt(element5, [3]),0,0);
        var morph3 = dom.createMorphAt(dom.childAt(element6, [1]),0,0);
        var morph4 = dom.createMorphAt(dom.childAt(element6, [3]),0,0);
        var morph5 = dom.createMorphAt(dom.childAt(element6, [5, 3]),0,0);
        var morph6 = dom.createMorphAt(element6,7,7);
        var morph7 = dom.createMorphAt(element7,0,0);
        var morph8 = dom.createMorphAt(element7,2,2);
        var morph9 = dom.createMorphAt(dom.childAt(element8, [1, 3]),0,0);
        var morph10 = dom.createMorphAt(dom.childAt(element8, [3, 3]),0,0);
        attribute(env, attrMorph0, element4, "src", concat(env, [get(env, context, "ownerProfilePicture")]));
        content(env, morph0, context, "title");
        block(env, morph1, context, "link-to", ["project.index"], {}, child0, null);
        block(env, morph2, context, "link-to", ["project.matching"], {}, child1, null);
        content(env, morph3, context, "title");
        content(env, morph4, context, "ownerName");
        content(env, morph5, context, "description");
        block(env, morph6, context, "each", [get(env, context, "matchingProfiles")], {"keyword": "profile"}, child2, null);
        content(env, morph7, context, "startdate");
        content(env, morph8, context, "enddate");
        content(env, morph9, context, "summary");
        content(env, morph10, context, "story");
        return fragment;
      }
    };
  }()));

});
define('xtalus/templates/project/matching', ['exports'], function (exports) {

  'use strict';

  exports['default'] = Ember.HTMLBars.template((function() {
    var child0 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Algemeen");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child1 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Matches");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          return fragment;
        }
      };
    }());
    var child2 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("        ");
          dom.appendChild(el0, el1);
          var el1 = dom.createElement("div");
          dom.setAttribute(el1,"style","clear:both;border-bottom:1px solid #ccc; padding: 10px 0;");
          var el2 = dom.createTextNode("\n            ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("div");
          dom.setAttribute(el2,"class","profilePicture");
          var el3 = dom.createTextNode("     ");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n            ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("div");
          var el3 = dom.createElement("strong");
          var el4 = dom.createComment("");
          dom.appendChild(el3, el4);
          dom.appendChild(el2, el3);
          var el3 = dom.createElement("br");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("geen match");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n        ");
          dom.appendChild(el1, el2);
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          var hooks = env.hooks, get = hooks.get, concat = hooks.concat, attribute = hooks.attribute, content = hooks.content;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          var element3 = dom.childAt(fragment, [1]);
          var element4 = dom.childAt(element3, [1]);
          var attrMorph0 = dom.createAttrMorph(element4, 'style');
          var morph0 = dom.createMorphAt(dom.childAt(element3, [3, 0]),0,0);
          attribute(env, attrMorph0, element4, "style", concat(env, ["float:left; margin-right:10px; margin-bottom:10px; background-image: url(", get(env, context, "ownerProfilePicture"), "); width:35px; height:35px;"]));
          content(env, morph0, context, "profile.name");
          return fragment;
        }
      };
    }());
    var child3 = (function() {
      return {
        isHTMLBars: true,
        revision: "Ember@1.11.1",
        blockParams: 0,
        cachedFragment: null,
        hasRendered: false,
        build: function build(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("                ");
          dom.appendChild(el0, el1);
          var el1 = dom.createElement("tr");
          var el2 = dom.createTextNode("\n                    ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("td");
          var el3 = dom.createElement("div");
          dom.setAttribute(el3,"class","profilePicture");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode(" ");
          dom.appendChild(el2, el3);
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n                    ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("td");
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n                    ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("td");
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n                ");
          dom.appendChild(el1, el2);
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        render: function render(context, env, contextualElement) {
          var dom = env.dom;
          var hooks = env.hooks, get = hooks.get, element = hooks.element, concat = hooks.concat, attribute = hooks.attribute, content = hooks.content;
          dom.detectNamespace(contextualElement);
          var fragment;
          if (env.useFragmentCache && dom.canClone) {
            if (this.cachedFragment === null) {
              fragment = this.build(dom);
              if (this.hasRendered) {
                this.cachedFragment = fragment;
              } else {
                this.hasRendered = true;
              }
            }
            if (this.cachedFragment) {
              fragment = dom.cloneNode(this.cachedFragment, true);
            }
          } else {
            fragment = this.build(dom);
          }
          var element0 = dom.childAt(fragment, [1]);
          var element1 = dom.childAt(element0, [1]);
          var element2 = dom.childAt(element1, [0]);
          var attrMorph0 = dom.createAttrMorph(element2, 'style');
          var morph0 = dom.createMorphAt(element1,2,2);
          var morph1 = dom.createMorphAt(dom.childAt(element0, [3]),0,0);
          var morph2 = dom.createMorphAt(dom.childAt(element0, [5]),0,0);
          element(env, element0, context, "action", ["showMatchDetails", get(env, context, "connection")], {});
          attribute(env, attrMorph0, element2, "style", concat(env, ["background-image: url(", get(env, context, "connection.profilePicture"), ") "]));
          content(env, morph0, context, "match.fullname");
          content(env, morph1, context, "match.branch");
          content(env, morph2, context, "match.roles");
          return fragment;
        }
      };
    }());
    return {
      isHTMLBars: true,
      revision: "Ember@1.11.1",
      blockParams: 0,
      cachedFragment: null,
      hasRendered: false,
      build: function build(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("section");
        dom.setAttribute(el1,"id","page");
        dom.setAttribute(el1,"class","profile");
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("header");
        dom.setAttribute(el2,"id","sub-bar");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("img");
        dom.setAttribute(el3,"class","profile-image");
        dom.setAttribute(el3,"width","100");
        dom.setAttribute(el3,"height","100");
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createTextNode("Project: ");
        dom.appendChild(el3, el4);
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("nav");
        dom.setAttribute(el3,"id","sub-nav");
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("ul");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("li");
        var el6 = dom.createComment("");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("aside");
        dom.setAttribute(el2,"id","user-info");
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h4");
        var el4 = dom.createTextNode("Specialismen");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h5");
        var el4 = dom.createTextNode("matches 0/");
        dom.appendChild(el3, el4);
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n");
        dom.appendChild(el2, el3);
        var el3 = dom.createComment("");
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("a");
        dom.setAttribute(el3,"style","display:block; padding:20px 0;");
        var el4 = dom.createTextNode("+ Specialisme toevoegen");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n    ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("section");
        dom.setAttribute(el2,"id","profile-data");
        var el3 = dom.createTextNode("\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h3");
        var el4 = dom.createTextNode("MatchingProfiel: ");
        dom.appendChild(el3, el4);
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n\n\n        ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("section");
        dom.setAttribute(el3,"id","matches");
        dom.setAttribute(el3,"class","list");
        var el4 = dom.createTextNode("\n\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("h3");
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode(" Matches");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n            ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("table");
        var el5 = dom.createTextNode("\n                ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("tr");
        var el6 = dom.createTextNode("\n                    ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("th");
        var el7 = dom.createTextNode("Naam");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n                    ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("th");
        var el7 = dom.createTextNode("Branch");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n                    ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("th");
        var el7 = dom.createTextNode("Rol");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n                ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n\n");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("            ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n        ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      render: function render(context, env, contextualElement) {
        var dom = env.dom;
        var hooks = env.hooks, get = hooks.get, concat = hooks.concat, attribute = hooks.attribute, content = hooks.content, block = hooks.block;
        dom.detectNamespace(contextualElement);
        var fragment;
        if (env.useFragmentCache && dom.canClone) {
          if (this.cachedFragment === null) {
            fragment = this.build(dom);
            if (this.hasRendered) {
              this.cachedFragment = fragment;
            } else {
              this.hasRendered = true;
            }
          }
          if (this.cachedFragment) {
            fragment = dom.cloneNode(this.cachedFragment, true);
          }
        } else {
          fragment = this.build(dom);
        }
        var element5 = dom.childAt(fragment, [0]);
        var element6 = dom.childAt(element5, [1]);
        var element7 = dom.childAt(element6, [1]);
        var element8 = dom.childAt(element6, [5, 1]);
        var element9 = dom.childAt(element5, [3]);
        var element10 = dom.childAt(element5, [5]);
        var element11 = dom.childAt(element10, [3]);
        var attrMorph0 = dom.createAttrMorph(element7, 'src');
        var morph0 = dom.createMorphAt(dom.childAt(element6, [3]),1,1);
        var morph1 = dom.createMorphAt(dom.childAt(element8, [1]),0,0);
        var morph2 = dom.createMorphAt(dom.childAt(element8, [3]),0,0);
        var morph3 = dom.createMorphAt(dom.childAt(element9, [3]),1,1);
        var morph4 = dom.createMorphAt(element9,5,5);
        var morph5 = dom.createMorphAt(dom.childAt(element10, [1]),1,1);
        var morph6 = dom.createMorphAt(dom.childAt(element11, [1]),0,0);
        var morph7 = dom.createMorphAt(dom.childAt(element11, [3]),3,3);
        attribute(env, attrMorph0, element7, "src", concat(env, [get(env, context, "ownerProfilePicture")]));
        content(env, morph0, context, "title");
        block(env, morph1, context, "link-to", ["project.index"], {}, child0, null);
        block(env, morph2, context, "link-to", ["project.matching"], {}, child1, null);
        content(env, morph3, context, "matchingProfiles.total");
        block(env, morph4, context, "each", [get(env, context, "matchingProfiles.list")], {"keyword": "profile"}, child2, null);
        content(env, morph5, context, "selectedProfile.title");
        content(env, morph6, context, "selectedProfile.matchesCount");
        block(env, morph7, context, "each", [get(env, context, "selectedProfile.matches")], {"keyword": "match"}, child3, null);
        return fragment;
      }
    };
  }()));

});
define('xtalus/tests/app.jshint', function () {

  'use strict';

  module('JSHint - .');
  test('app.js should pass jshint', function() { 
    ok(true, 'app.js should pass jshint.'); 
  });

});
define('xtalus/tests/controllers/login.jshint', function () {

  'use strict';

  module('JSHint - controllers');
  test('controllers/login.js should pass jshint', function() { 
    ok(true, 'controllers/login.js should pass jshint.'); 
  });

});
define('xtalus/tests/controllers/me.jshint', function () {

  'use strict';

  module('JSHint - controllers');
  test('controllers/me.js should pass jshint', function() { 
    ok(true, 'controllers/me.js should pass jshint.'); 
  });

});
define('xtalus/tests/controllers/me/connections.jshint', function () {

  'use strict';

  module('JSHint - controllers/me');
  test('controllers/me/connections.js should pass jshint', function() { 
    ok(true, 'controllers/me/connections.js should pass jshint.'); 
  });

});
define('xtalus/tests/controllers/me/projects.jshint', function () {

  'use strict';

  module('JSHint - controllers/me');
  test('controllers/me/projects.js should pass jshint', function() { 
    ok(true, 'controllers/me/projects.js should pass jshint.'); 
  });

});
define('xtalus/tests/controllers/profile.jshint', function () {

  'use strict';

  module('JSHint - controllers');
  test('controllers/profile.js should pass jshint', function() { 
    ok(true, 'controllers/profile.js should pass jshint.'); 
  });

});
define('xtalus/tests/controllers/profile/connections.jshint', function () {

  'use strict';

  module('JSHint - controllers/profile');
  test('controllers/profile/connections.js should pass jshint', function() { 
    ok(true, 'controllers/profile/connections.js should pass jshint.'); 
  });

});
define('xtalus/tests/controllers/profile/index.jshint', function () {

  'use strict';

  module('JSHint - controllers/profile');
  test('controllers/profile/index.js should pass jshint', function() { 
    ok(true, 'controllers/profile/index.js should pass jshint.'); 
  });

});
define('xtalus/tests/controllers/profile/projects.jshint', function () {

  'use strict';

  module('JSHint - controllers/profile');
  test('controllers/profile/projects.js should pass jshint', function() { 
    ok(true, 'controllers/profile/projects.js should pass jshint.'); 
  });

});
define('xtalus/tests/controllers/project.jshint', function () {

  'use strict';

  module('JSHint - controllers');
  test('controllers/project.js should pass jshint', function() { 
    ok(true, 'controllers/project.js should pass jshint.'); 
  });

});
define('xtalus/tests/controllers/project/index.jshint', function () {

  'use strict';

  module('JSHint - controllers/project');
  test('controllers/project/index.js should pass jshint', function() { 
    ok(false, 'controllers/project/index.js should pass jshint.\ncontrollers/project/index.js: line 2, col 1, \'$\' is defined but never used.\n\n1 error'); 
  });

});
define('xtalus/tests/controllers/project/matching.jshint', function () {

  'use strict';

  module('JSHint - controllers/project');
  test('controllers/project/matching.js should pass jshint', function() { 
    ok(false, 'controllers/project/matching.js should pass jshint.\ncontrollers/project/matching.js: line 2, col 1, \'$\' is defined but never used.\n\n1 error'); 
  });

});
define('xtalus/tests/helpers/resolver', ['exports', 'ember/resolver', 'xtalus/config/environment'], function (exports, Resolver, config) {

  'use strict';

  var resolver = Resolver['default'].create();

  resolver.namespace = {
    modulePrefix: config['default'].modulePrefix,
    podModulePrefix: config['default'].podModulePrefix
  };

  exports['default'] = resolver;

});
define('xtalus/tests/helpers/resolver.jshint', function () {

  'use strict';

  module('JSHint - helpers');
  test('helpers/resolver.js should pass jshint', function() { 
    ok(true, 'helpers/resolver.js should pass jshint.'); 
  });

});
define('xtalus/tests/helpers/start-app', ['exports', 'ember', 'xtalus/app', 'xtalus/router', 'xtalus/config/environment'], function (exports, Ember, Application, Router, config) {

  'use strict';



  exports['default'] = startApp;
  function startApp(attrs) {
    var application;

    var attributes = Ember['default'].merge({}, config['default'].APP);
    attributes = Ember['default'].merge(attributes, attrs); // use defaults, but you can override;

    Ember['default'].run(function () {
      application = Application['default'].create(attributes);
      application.setupForTesting();
      application.injectTestHelpers();
    });

    return application;
  }

});
define('xtalus/tests/helpers/start-app.jshint', function () {

  'use strict';

  module('JSHint - helpers');
  test('helpers/start-app.js should pass jshint', function() { 
    ok(true, 'helpers/start-app.js should pass jshint.'); 
  });

});
define('xtalus/tests/router.jshint', function () {

  'use strict';

  module('JSHint - .');
  test('router.js should pass jshint', function() { 
    ok(false, 'router.js should pass jshint.\nrouter.js: line 22, col 31, Missing semicolon.\nrouter.js: line 23, col 7, Missing semicolon.\n\n2 errors'); 
  });

});
define('xtalus/tests/routes/application.jshint', function () {

  'use strict';

  module('JSHint - routes');
  test('routes/application.js should pass jshint', function() { 
    ok(true, 'routes/application.js should pass jshint.'); 
  });

});
define('xtalus/tests/routes/login.jshint', function () {

  'use strict';

  module('JSHint - routes');
  test('routes/login.js should pass jshint', function() { 
    ok(true, 'routes/login.js should pass jshint.'); 
  });

});
define('xtalus/tests/routes/me.jshint', function () {

  'use strict';

  module('JSHint - routes');
  test('routes/me.js should pass jshint', function() { 
    ok(true, 'routes/me.js should pass jshint.'); 
  });

});
define('xtalus/tests/routes/me/connections.jshint', function () {

  'use strict';

  module('JSHint - routes/me');
  test('routes/me/connections.js should pass jshint', function() { 
    ok(true, 'routes/me/connections.js should pass jshint.'); 
  });

});
define('xtalus/tests/routes/me/index.jshint', function () {

  'use strict';

  module('JSHint - routes/me');
  test('routes/me/index.js should pass jshint', function() { 
    ok(true, 'routes/me/index.js should pass jshint.'); 
  });

});
define('xtalus/tests/routes/me/projects.jshint', function () {

  'use strict';

  module('JSHint - routes/me');
  test('routes/me/projects.js should pass jshint', function() { 
    ok(true, 'routes/me/projects.js should pass jshint.'); 
  });

});
define('xtalus/tests/routes/profile.jshint', function () {

  'use strict';

  module('JSHint - routes');
  test('routes/profile.js should pass jshint', function() { 
    ok(true, 'routes/profile.js should pass jshint.'); 
  });

});
define('xtalus/tests/routes/profile/connections.jshint', function () {

  'use strict';

  module('JSHint - routes/profile');
  test('routes/profile/connections.js should pass jshint', function() { 
    ok(true, 'routes/profile/connections.js should pass jshint.'); 
  });

});
define('xtalus/tests/routes/profile/index.jshint', function () {

  'use strict';

  module('JSHint - routes/profile');
  test('routes/profile/index.js should pass jshint', function() { 
    ok(true, 'routes/profile/index.js should pass jshint.'); 
  });

});
define('xtalus/tests/routes/profile/projects.jshint', function () {

  'use strict';

  module('JSHint - routes/profile');
  test('routes/profile/projects.js should pass jshint', function() { 
    ok(true, 'routes/profile/projects.js should pass jshint.'); 
  });

});
define('xtalus/tests/routes/project.jshint', function () {

  'use strict';

  module('JSHint - routes');
  test('routes/project.js should pass jshint', function() { 
    ok(false, 'routes/project.js should pass jshint.\nroutes/project.js: line 15, col 41, Expected an assignment or function call and instead saw an expression.\nroutes/project.js: line 16, col 39, Missing semicolon.\nroutes/project.js: line 25, col 43, \'model\' is defined but never used.\n\n3 errors'); 
  });

});
define('xtalus/tests/routes/project/index.jshint', function () {

  'use strict';

  module('JSHint - routes/project');
  test('routes/project/index.js should pass jshint', function() { 
    ok(false, 'routes/project/index.js should pass jshint.\nroutes/project/index.js: line 10, col 10, Missing semicolon.\nroutes/project/index.js: line 13, col 57, Missing semicolon.\nroutes/project/index.js: line 14, col 28, Missing semicolon.\nroutes/project/index.js: line 26, col 15, Missing semicolon.\nroutes/project/index.js: line 27, col 11, Missing semicolon.\nroutes/project/index.js: line 19, col 13, \'activePerson\' is defined but never used.\n\n6 errors'); 
  });

});
define('xtalus/tests/routes/project/matching.jshint', function () {

  'use strict';

  module('JSHint - routes/project');
  test('routes/project/matching.js should pass jshint', function() { 
    ok(false, 'routes/project/matching.js should pass jshint.\nroutes/project/matching.js: line 10, col 10, Missing semicolon.\nroutes/project/matching.js: line 13, col 57, Missing semicolon.\nroutes/project/matching.js: line 14, col 28, Missing semicolon.\nroutes/project/matching.js: line 29, col 15, Missing semicolon.\nroutes/project/matching.js: line 30, col 11, Missing semicolon.\nroutes/project/matching.js: line 19, col 13, \'activePerson\' is defined but never used.\n\n6 errors'); 
  });

});
define('xtalus/tests/test-helper', ['xtalus/tests/helpers/resolver', 'ember-qunit'], function (resolver, ember_qunit) {

	'use strict';

	ember_qunit.setResolver(resolver['default']);

});
define('xtalus/tests/test-helper.jshint', function () {

  'use strict';

  module('JSHint - .');
  test('test-helper.js should pass jshint', function() { 
    ok(true, 'test-helper.js should pass jshint.'); 
  });

});
define('xtalus/tests/views/me.jshint', function () {

  'use strict';

  module('JSHint - views');
  test('views/me.js should pass jshint', function() { 
    ok(true, 'views/me.js should pass jshint.'); 
  });

});
define('xtalus/tests/views/profile.jshint', function () {

  'use strict';

  module('JSHint - views');
  test('views/profile.js should pass jshint', function() { 
    ok(true, 'views/profile.js should pass jshint.'); 
  });

});
define('xtalus/tests/views/project.jshint', function () {

  'use strict';

  module('JSHint - views');
  test('views/project.js should pass jshint', function() { 
    ok(true, 'views/project.js should pass jshint.'); 
  });

});
define('xtalus/views/me', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var UserView = Ember['default'].View.extend({
        layoutName: 'layout/main',
        templateName: 'me' });

    exports['default'] = UserView;

});
define('xtalus/views/profile', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProfileView = Ember['default'].View.extend({
        layoutName: 'layout/main' });

    exports['default'] = ProfileView;

});
define('xtalus/views/project', ['exports', 'ember'], function (exports, Ember) {

    'use strict';

    var ProjectView = Ember['default'].View.extend({
        layoutName: 'layout/main' });

    exports['default'] = ProjectView;

});
/* jshint ignore:start */

/* jshint ignore:end */

/* jshint ignore:start */

define('xtalus/config/environment', ['ember'], function(Ember) {
  var prefix = 'xtalus';
/* jshint ignore:start */

try {
  var metaName = prefix + '/config/environment';
  var rawConfig = Ember['default'].$('meta[name="' + metaName + '"]').attr('content');
  var config = JSON.parse(unescape(rawConfig));

  return { 'default': config };
}
catch(err) {
  throw new Error('Could not read config from meta tag with name "' + metaName + '".');
}

/* jshint ignore:end */

});

if (runningTests) {
  require("xtalus/tests/test-helper");
} else {
  require("xtalus/app")["default"].create({"name":"xtalus","version":"0.0.0.a76ac732"});
}

/* jshint ignore:end */
//# sourceMappingURL=xtalus.map