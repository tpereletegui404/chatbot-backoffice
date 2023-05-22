export class ModelTracker {
    public changes = []

    onModelChanged = (model) => {
        this.changes.push({ ...model })
    }
}
