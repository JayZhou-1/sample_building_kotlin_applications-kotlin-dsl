package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.internal.artifacts.dependencies.ProjectDependencyInternal;
import org.gradle.api.internal.artifacts.DefaultProjectDependencyFactory;
import org.gradle.api.internal.artifacts.dsl.dependencies.ProjectFinder;
import org.gradle.api.internal.catalog.DelegatingProjectDependency;
import org.gradle.api.internal.catalog.TypeSafeProjectDependencyFactory;
import javax.inject.Inject;

@NonNullApi
public class DemoProjectDependency extends DelegatingProjectDependency {

    @Inject
    public DemoProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":app"
     */
    public AppProjectDependency getApp() { return new AppProjectDependency(getFactory(), create(":app")); }

    /**
     * Creates a project dependency on the project at path ":app-common"
     */
    public AppCommonProjectDependency getAppCommon() { return new AppCommonProjectDependency(getFactory(), create(":app-common")); }

}
