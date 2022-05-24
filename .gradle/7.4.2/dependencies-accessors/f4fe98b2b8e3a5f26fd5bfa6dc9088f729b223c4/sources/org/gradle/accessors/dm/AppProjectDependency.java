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
public class AppProjectDependency extends DelegatingProjectDependency {

    @Inject
    public AppProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":app:inner-app"
     */
    public App_InnerAppProjectDependency getInnerApp() { return new App_InnerAppProjectDependency(getFactory(), create(":app:inner-app")); }

    /**
     * Creates a project dependency on the project at path ":app:test-app"
     */
    public App_TestAppProjectDependency getTestApp() { return new App_TestAppProjectDependency(getFactory(), create(":app:test-app")); }

}
