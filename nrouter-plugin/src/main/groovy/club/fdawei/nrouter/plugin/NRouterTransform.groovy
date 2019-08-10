package club.fdawei.nrouter.plugin

import club.fdawei.nrouter.plugin.handler.ProviderCollector
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Project

/**
 * Created by david on 2019/05/28.
 */
class NRouterTransform extends Transform {

    private Project project

    NRouterTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return 'nRouter'
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws
            TransformException, InterruptedException, IOException {
        ProviderCollector.of(project, transformInvocation).handle()
    }
}
